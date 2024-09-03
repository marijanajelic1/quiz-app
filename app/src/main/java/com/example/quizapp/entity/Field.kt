package com.example.quizapp.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "field")
data class Field(
    @PrimaryKey
    @NonNull
    val id: Int = 1,
    @ColumnInfo(name = "name")
    val name: String

)