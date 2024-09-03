package com.example.quizapp.data.repository

import com.example.quizapp.entity.Field
import kotlinx.coroutines.flow.Flow

interface FieldRepository {

    fun getAllFields(): Flow<List<Field>>
    fun getField(id: Int): Flow<Field>
    suspend fun insert(field: Field)
    suspend fun delete(field: Field)
    suspend fun update(field: Field)
}