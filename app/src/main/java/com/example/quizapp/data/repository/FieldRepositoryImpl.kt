package com.example.quizapp.data.repository

import com.example.quizapp.data.dao.FieldDao
import com.example.quizapp.entity.Field
import kotlinx.coroutines.flow.Flow

class FieldRepositoryImpl(private val fieldDao: FieldDao) : FieldRepository {

    override fun getAllFields(): Flow<List<Field>> {
        return fieldDao.getAllFields()
    }

    override fun getField(id: Int): Flow<Field> {
        return fieldDao.getField(id)
    }

    override suspend fun insert(field: Field) {
        fieldDao.insert(field)
    }

    override suspend fun delete(field: Field) {
        fieldDao.delete(field)
    }

    override suspend fun update(field: Field) {
        fieldDao.update(field)
    }
}