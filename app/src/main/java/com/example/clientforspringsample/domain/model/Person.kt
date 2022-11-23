package com.example.clientforspringsample.domain.model

data class Person(
    val id: Long = 0L,
    val name: String,
    val email: String,
    val age: Int,
)