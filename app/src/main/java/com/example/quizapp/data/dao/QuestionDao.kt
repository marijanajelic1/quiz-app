package com.example.quizapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.quizapp.entity.Question
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao {

    @Insert
    suspend fun insert(question: Question)

    @Update
    suspend fun update(question: Question)

    @Delete
    suspend fun delete(question: Question)

    @Query("SELECT * FROM (SELECT * FROM question WHERE field_id = :fieldId ORDER BY RANDOM() LIMIT 5) AS random_questions ORDER BY id")
    fun getQuestionsByFieldId(fieldId: Int): Flow<List<Question>>

    @Query("SELECT * FROM question")
    fun getAllQuestions(): Flow<List<Question>>

    @Query("SELECT * FROM question WHERE text = :text LIMIT 1")
    suspend fun getQuestionByText(text: String): Question?
}