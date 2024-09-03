package com.example.quizapp.data.repository

import com.example.quizapp.entity.Question
import kotlinx.coroutines.flow.Flow

interface QuestionRepository {

    fun getAllQuestions(): Flow<List<Question>>
    suspend fun insert(question: Question)
    suspend fun delete(question: Question)
    suspend fun update(question: Question)
    fun getQuestionsByFieldId(fieldId: Int): Flow<List<Question>>
    suspend fun getQuestionByText(text: String): Question?
}