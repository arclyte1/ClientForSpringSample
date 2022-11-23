package com.example.clientforspringsample.domain.usecase

import com.example.clientforspringsample.common.Resource
import com.example.clientforspringsample.domain.repository.PersonRepository
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class DeletePersonUseCase @Inject constructor(
    private val repository: PersonRepository
) {

    operator fun invoke(
        id: Long
    ) = flow {
        try {
            emit(Resource.Loading())
            val result = repository.delete(id)
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Failed to remove person"))
        }
    }
}