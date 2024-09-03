package com.example.quizapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.quizapp.QuizAplication
import com.example.quizapp.data.repository.FieldRepository
import com.example.quizapp.data.repository.QuestionRepository
import com.example.quizapp.entity.Field
import com.example.quizapp.entity.Question
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddQuestionsViewModel(
    private val questionRepository: QuestionRepository,
    private val fieldRepository: FieldRepository
) : ViewModel() {

    val fields: Flow<List<Field>> = fieldRepository.getAllFields()
    private val _uiState = MutableStateFlow(AddQuestionUiState())
    val uiState: StateFlow<AddQuestionUiState> = _uiState

    fun onAddQuestion(selectedField: Field?, questionText: String, answerText: String) {
        viewModelScope.launch {
            if (validateInput(selectedField, questionText, answerText)) {
                try {
                    val existingQuestion = questionRepository.getQuestionByText(questionText)
                    if (existingQuestion != null) {
                        _uiState.value = AddQuestionUiState(
                            isSuccess = false,
                            message = "Pitanje već postoji u bazi"
                        )
                        return@launch
                    }

                    val newQuestion = Question(
                        text = questionText,
                        answer = answerText,
                        fieldId = selectedField!!.id
                    )
                    questionRepository.insert(newQuestion)
                    _uiState.value = AddQuestionUiState(
                        isSuccess = true,
                        message = "Pitanje uspešno dodato!"
                    )
                } catch (e: Exception) {
                    _uiState.value = AddQuestionUiState(
                        isSuccess = false,
                        message = "Greška prilikom dodavanja pitanja"
                    )
                }
            }
        }
    }

    private fun validateInput(
        selectedField: Field?,
        questionText: String,
        answerText: String
    ): Boolean {
        return when {
            selectedField == null -> {
                _uiState.value = AddQuestionUiState(
                    isSuccess = false,
                    message = "Molimo izaberite oblast"
                )
                false
            }

            questionText.isBlank() || answerText.isBlank() -> {
                _uiState.value = AddQuestionUiState(
                    isSuccess = false,
                    message = "Tekst pitanja i odgovor ne mogu biti prazni"
                )
                false
            }

            questionText.length !in 10..130 -> {
                _uiState.value = AddQuestionUiState(
                    isSuccess = false,
                    message = "Dužina teksta pitanja mora biti između 10 i 130 karaktera"
                )
                false
            }

            answerText.length !in 3..20 -> {
                _uiState.value = AddQuestionUiState(
                    isSuccess = false,
                    message = "Dužina odgovora pitanja mora biti između 3 i 20 karaktera"
                )
                false
            }

            else -> true
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as QuizAplication)
                AddQuestionsViewModel(
                    application.container.questionRepository,
                    application.container.fieldRepository
                )
            }
        }
    }
}

data class AddQuestionUiState(
    val isSuccess: Boolean = false,
    val message: String? = null
)
