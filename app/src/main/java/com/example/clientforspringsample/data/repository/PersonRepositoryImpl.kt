package com.example.clientforspringsample.data.repository

import com.example.clientforspringsample.data.remote.PersonApi
import com.example.clientforspringsample.data.remote.dto.CreatePersonRequest
import com.example.clientforspringsample.data.remote.dto.UpdatePersonRequest
import com.example.clientforspringsample.domain.model.Person
import com.example.clientforspringsample.domain.repository.PersonRepository
import javax.inject.Inject

class PersonRepositoryImpl @Inject constructor(
    private val api: PersonApi
) : PersonRepository {

    override suspend fun getAll(): List<Person> {
        return api.getAll().map { it.toPerson() }
    }

    override suspend fun update(person: Person): Person {
        val request = UpdatePersonRequest(
            name = person.name,
            email = person.email,
            age = person.age,
        )
        return api.update(
            id = person.id,
            request = request,
        ).toPerson()
    }

    override suspend fun create(person: Person): Person {
        val request = CreatePersonRequest(
            name = person.name,
            email = person.email,
            age = person.age,
        )
        return api.create(
            request = request
        ).toPerson()
    }

    override suspend fun delete(id: Long) {
        api.delete(id)
    }
}