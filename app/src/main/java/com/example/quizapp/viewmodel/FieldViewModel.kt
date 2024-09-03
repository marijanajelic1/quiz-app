package com.example.quizapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.quizapp.QuizAplication
import com.example.quizapp.data.repository.FieldRepository
import com.example.quizapp.entity.Field
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class FieldUiState(
    val fieldList: List<Field> = emptyList()
)

class FieldViewModel(private val fieldRepository: FieldRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(FieldUiState())
    val uiState: StateFlow<FieldUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            fieldRepository.getAllFields()
                .collect { fields ->
                    _uiState.value = FieldUiState(fieldList = fields)
                }
        }
    }

    fun insert(field: Field) = viewModelScope.launch {
        fieldRepository.insert(field)
    }

    fun delete(field: Field) = viewModelScope.launch {
        fieldRepository.delete(field)
    }

    fun update(field: Field) = viewModelScope.launch {
        fieldRepository.update(field)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as QuizAplication)
                FieldViewModel(application.container.fieldRepository)
            }
        }
    }
}
