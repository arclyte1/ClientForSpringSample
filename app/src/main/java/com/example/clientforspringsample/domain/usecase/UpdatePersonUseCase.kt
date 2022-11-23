package com.example.clientforspringsample.domain.usecase

import com.example.clientforspringsample.common.Resource
import com.example.clientforspringsample.domain.model.Person
import com.example.clientforspringsample.domain.repository.PersonRepository
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class UpdatePersonUseCase @Inject constructor(
    private val repository: PersonRepository
) {

    operator fun invoke(
        person: Person
    ) = flow {
        try {
            emit(Resource.Loading())
            val result = repository.update(person)
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Failed to update person"))
        }
    }
}