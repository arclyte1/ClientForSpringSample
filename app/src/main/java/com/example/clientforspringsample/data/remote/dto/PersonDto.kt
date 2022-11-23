package com.example.clientforspringsample.data.remote.dto

import com.example.clientforspringsample.domain.model.Person

data class PersonDto(
    val id: Long,
    val name: String,
    val email: String,
    val age: Int,
) {
    fun toPerson() = Person(
        id = id,
        name = name,
        email = email,
        age = age,
    )
}