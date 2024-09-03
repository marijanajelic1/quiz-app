package com.example.quizapp.data

import android.content.Context
import com.example.quizapp.data.repository.FieldRepository
import com.example.quizapp.data.repository.FieldRepositoryImpl
import com.example.quizapp.data.repository.QuestionRepository
import com.example.quizapp.data.repository.QuestionRepositoryImpl

interface AppContainer {
    val fieldRepository: FieldRepository
    val questionRepository: QuestionRepository
}

class AppDataContainer(private val context: Context) : AppContainer {

    override val fieldRepository: FieldRepository by lazy {
        FieldRepositoryImpl(QuizDatabase.getQuizDatabase(context).fieldDao())
    }

    override val questionRepository: QuestionRepository by lazy {
        QuestionRepositoryImpl(QuizDatabase.getQuizDatabase(context).questionDao())
    }

}
