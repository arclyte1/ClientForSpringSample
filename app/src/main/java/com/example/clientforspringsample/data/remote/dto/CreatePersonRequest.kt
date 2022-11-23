package com.example.clientforspringsample.data.remote.dto

data class CreatePersonRequest(
    val name: String,
    val email: String,
    val age: Int,
)