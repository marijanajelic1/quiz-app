package com.example.quizapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.quizapp.entity.Field
import kotlinx.coroutines.flow.Flow


@Dao
interface FieldDao {

    @Insert
    suspend fun insert(field: Field)

    @Update
    suspend fun update(field: Field)

    @Delete
    suspend fun delete(field: Field)

    @Query("SELECT * FROM field WHERE id = :id")
    fun getField(id: Int): Flow<Field>

    @Query("SELECT * FROM field ORDER BY id DESC")
    fun getAllFields(): Flow<List<Field>>
}