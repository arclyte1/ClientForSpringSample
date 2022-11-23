package com.example.clientforspringsample.domain.repository

import com.example.clientforspringsample.domain.model.Person

interface PersonRepository {

    suspend fun getAll(): List<Person>

    suspend fun update(person: Person): Person

    suspend fun create(person: Person): Person

    suspend fun delete(id: Long)
}