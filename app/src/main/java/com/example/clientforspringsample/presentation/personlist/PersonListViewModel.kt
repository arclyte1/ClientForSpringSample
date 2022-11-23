package com.example.clientforspringsample.presentation.personlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clientforspringsample.common.Resource
import com.example.clientforspringsample.domain.model.Person
import com.example.clientforspringsample.domain.usecase.CreatePersonUseCase
import com.example.clientforspringsample.domain.usecase.DeletePersonUseCase
import com.example.clientforspringsample.domain.usecase.GetAllPersonsUseCase
import com.example.clientforspringsample.domain.usecase.UpdatePersonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PersonListViewModel @Inject constructor(
    private val getAllPersonsUseCase: GetAllPersonsUseCase,
    private val createPersonUseCase: CreatePersonUseCase,
    private val updatePersonUseCase: UpdatePersonUseCase,
    private val deletePersonUseCase: DeletePersonUseCase,
) : ViewModel() {

    val personList = MutableStateFlow<List<Person>>(emptyList())

    val isLoading = MutableStateFlow(false)

    val error = MutableStateFlow<String?>(null)

    fun getAll() {
        getAllPersonsUseCase().onEach { result ->
            when(result) {
                is Resource.Loading -> isLoading.value = true
                is Resource.Success -> {
                    isLoading.value = false
                    personList.value = result.data ?: emptyList()
                }
                is Resource.Error -> {
                    isLoading.value = false
                    error.value = result.message
                }
            }
        }.launchIn(viewModelScope)
    }

    fun create(person: Person) {
        createPersonUseCase(person).onEach { result ->
            when(result) {
                is Resource.Loading -> isLoading.value = true
                is Resource.Success -> {
                    isLoading.value = false
                    result.data?.let { person ->
                        val list = personList.value.toMutableList()
                        list.add(person)
                        personList.value = list.toList()
                    }
                }
                is Resource.Error -> {
                    isLoading.value = false
                    error.value = result.message
                }
            }

        }.launchIn(viewModelScope)
    }

    fun update(person: Person) {
        updatePersonUseCase(person).onEach { result ->
            when(result) {
                is Resource.Loading -> isLoading.value = true
                is Resource.Success -> {
                    isLoading.value = false
                    result.data?.let { person ->
                        val list = personList.value.toMutableList()
                        list.replaceAll {
                            if (it.id == person.id)
                                return@replaceAll person
                            it
                        }
                        personList.value = list.toList()
                    }
                }
                is Resource.Error -> {
                    isLoading.value = false
                    error.value = result.message
                }
            }

        }.launchIn(viewModelScope)
    }

    fun delete(id: Long) {
        deletePersonUseCase(id).onEach { result ->
            when(result) {
                is Resource.Loading -> isLoading.value = true
                is Resource.Success -> {
                    isLoading.value = false
                    val list = personList.value.toMutableList()
                    list.removeAll { it.id == id }
                    personList.value = list.toList()
                }
                is Resource.Error -> {
                    isLoading.value = false
                    error.value = result.message
                }
            }
        }.launchIn(viewModelScope)
    }
}