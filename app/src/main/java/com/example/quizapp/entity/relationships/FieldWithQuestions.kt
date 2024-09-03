package com.example.quizapp.entity.relationships

import androidx.room.Embedded
import androidx.room.Relation
import com.example.quizapp.entity.Field
import com.example.quizapp.entity.Question

data class FieldWithQuestions(
    @Embedded val field: Field,
    @Relation(
        parentColumn = "id",
        entityColumn = "field_id"
    )
    val questions: List<Question>
)