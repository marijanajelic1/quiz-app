package com.example.quizapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.quizapp.QuizAplication
import com.example.quizapp.data.repository.QuestionRepository
import com.example.quizapp.entity.Question
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DeleteQuestionsViewModel(
    private val questionRepository: QuestionRepository
) : ViewModel() {

    val questions: Flow<List<Question>> = questionRepository.getAllQuestions()
    private val _uiState = MutableStateFlow(DeleteQuestionUiState())
    val uiState: StateFlow<DeleteQuestionUiState> = _uiState

    var questionToDelete: Question? = null

    fun onDeleteQuestion(question: Question) {
        questionToDelete = question
        _uiState.value = DeleteQuestionUiState(showConfirmationDialog = true)
    }

    fun onConfirmDelete() = viewModelScope.launch {
        questionToDelete?.let {
            try {
                questionRepository.delete(it)
                _uiState.value = DeleteQuestionUiState(
                    showConfirmationDialog = false,
                    isDeleted = true
                )
            } catch (e: Exception) {
                Log.e("DeleteQuestionsViewModel", "Greska prilikom brisanja pitanja.", e)
                _uiState.value = DeleteQuestionUiState(
                    showConfirmationDialog = false,
                    errorMessage = "Greska prilikom brisanja pitanja."
                )
            }
        }
    }

    fun onCancelDelete() {
        _uiState.value = DeleteQuestionUiState(showConfirmationDialog = false)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as QuizAplication)
                DeleteQuestionsViewModel(
                    application.container.questionRepository
                )
            }
        }
    }
}

data class DeleteQuestionUiState(
    val showConfirmationDialog: Boolean = false,
    val isDeleted: Boolean = false,
    val errorMessage: String? = null
)
