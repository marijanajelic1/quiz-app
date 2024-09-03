package com.example.quizapp.data.repository

import com.example.quizapp.data.dao.QuestionDao
import com.example.quizapp.entity.Question
import kotlinx.coroutines.flow.Flow

class QuestionRepositoryImpl(private val questionDao: QuestionDao) : QuestionRepository {
    override fun getAllQuestions(): Flow<List<Question>> {
        return questionDao.getAllQuestions()
    }

    override suspend fun insert(question: Question) {
        questionDao.insert(question)
    }

    override suspend fun delete(question: Question) {
        questionDao.delete(question)
    }

    override suspend fun update(question: Question) {
        questionDao.update(question)
    }

    override fun getQuestionsByFieldId(fieldId: Int): Flow<List<Question>> {
        return questionDao.getQuestionsByFieldId(fieldId)
    }

    override suspend fun getQuestionByText(text: String): Question? {
        return questionDao.getQuestionByText(text)
    }

}