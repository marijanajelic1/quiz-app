package com.example.quizapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.quizapp.data.dao.FieldDao
import com.example.quizapp.data.dao.QuestionDao
import com.example.quizapp.entity.Field
import com.example.quizapp.entity.Question

@Database(entities = [Field::class, Question::class], version = 2, exportSchema = true)
abstract class QuizDatabase : RoomDatabase() {

    abstract fun fieldDao(): FieldDao
    abstract fun questionDao(): QuestionDao

    companion object {
        @Volatile
        private var Instance: QuizDatabase? = null

        fun getQuizDatabase(context: Context): QuizDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context = context,
                    klass = QuizDatabase::class.java,
                    name = "quiz_database"
                )
                    .addCallback(PrepopulateRoomCallback(context))
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}