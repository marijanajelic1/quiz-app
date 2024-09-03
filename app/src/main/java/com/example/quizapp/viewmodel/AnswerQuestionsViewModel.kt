package com.example.quizapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.quizapp.QuizAplication
import com.example.quizapp.data.repository.QuestionRepository
import com.example.quizapp.entity.Question
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AnswerQuestionsUiState(
    val questions: List<Question> = emptyList(),
    val answers: Map<Int, String> = emptyMap(),
    val correctAnswersCount: Int = 0,
    val showDialog: Boolean = false
)

class AnswerQuestionsViewModel(private val questionRepository: QuestionRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(AnswerQuestionsUiState())
    val uiState: StateFlow<AnswerQuestionsUiState> = _uiState.asStateFlow()

    fun getQuestionsByFieldId(fieldId: Int) {
        viewModelScope.launch {
            questionRepository.getQuestionsByFieldId(fieldId).collect { questions ->
                _uiState.update { currentState ->
                    currentState.copy(questions = questions)
                }
            }
        }
    }

    fun updateAnswer(questionId: Int, answer: String) {
        _uiState.update { currentState ->
            val updatedAnswers = currentState.answers.toMutableMap()
            updatedAnswers[questionId] = normalizeAnswer(answer)
            currentState.copy(answers = updatedAnswers)
        }
    }

    fun checkAnswers() {
        val correctCount = _uiState.value.questions.count { question ->
            _uiState.value.answers[question.id] == question.answer
        }
        _uiState.update { currentState ->
            currentState.copy(
                correctAnswersCount = correctCount,
                showDialog = true
            )
        }
    }

    fun dismissDialog() {
        _uiState.update { currentState ->
            currentState.copy(showDialog = false)
        }
    }

    fun normalizeAnswer(answer: String): String {
        return answer
            .replace("š", "s")
            .replace("č", "c")
            .replace("ć", "c")
            .replace("ž", "z")
            .replace("đ", "dj")
            .replace("Š", "S")
            .replace("Č", "C")
            .replace("Ć", "C")
            .replace("Ž", "Z")
            .replace("Đ", "Dj")
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as QuizAplication)
                AnswerQuestionsViewModel(application.container.questionRepository)
            }
        }
    }
}
