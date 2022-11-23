package com.example.clientforspringsample.domain.usecase

import com.example.clientforspringsample.common.Resource
import com.example.clientforspringsample.domain.repository.PersonRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllPersonsUseCase @Inject constructor(
    private val repository: PersonRepository
) {

    operator fun invoke() = flow {
        try {
            emit(Resource.Loading())
            val result = repository.getAll()
            emit(Resource.Success(result))
        } catch (e: java.lang.Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Failed to get all persons"))
        }
    }
}