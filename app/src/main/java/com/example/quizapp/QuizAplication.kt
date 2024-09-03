package com.example.quizapp

import android.app.Application
import com.example.quizapp.data.AppContainer
import com.example.quizapp.data.AppDataContainer
import com.example.quizapp.data.QuizDatabase

class QuizAplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container=AppDataContainer(this)
    }
}