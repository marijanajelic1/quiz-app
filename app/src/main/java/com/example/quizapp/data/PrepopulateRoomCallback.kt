package com.example.quizapp.data

import android.content.Context
import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.quizapp.R
import com.example.quizapp.entity.Field
import com.example.quizapp.entity.Question
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray

class PrepopulateRoomCallback(private val context: Context) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        CoroutineScope(Dispatchers.IO).launch {
            prePopulateFields(context)
        }
    }

    suspend fun prePopulateFields(context: Context) {
        try {
            val fieldDao = QuizDatabase.getQuizDatabase(context).fieldDao()
            val fieldList: JSONArray =
                context.resources.openRawResource(R.raw.fields).bufferedReader().use {
                    JSONArray(it.readText())
                }

            fieldList.takeIf { it.length() > 0 }?.let { list ->
                for (index in 0 until list.length()) {
                    val fieldObj = list.getJSONObject(index)
                    fieldDao.insert(
                        Field(
                            fieldObj.getInt("id"),
                            fieldObj.getString("name")
                        )
                    )

                }
            }

            val questionDao = QuizDatabase.getQuizDatabase(context).questionDao()
            val questionList: JSONArray =
                context.resources.openRawResource(R.raw.questions).bufferedReader().use {
                    JSONArray(it.readText())
                }

            questionList.takeIf { it.length() > 0 }?.let { list ->
                for (index in 0 until list.length()) {
                    val questionObj = list.getJSONObject(index)
                    val question = Question(
                        text = questionObj.getString("text"),
                        answer = questionObj.getString("answer"),
                        fieldId = questionObj.getInt("fieldId")
                    )
                    questionDao.insert(question)
                }
            }

        } catch (exception: Exception) {
            Log.e(
                "Quiz App",
                exception.localizedMessage
                    ?: "failed to pre-populate Fields and Questions into database"
            )
        }
    }
}