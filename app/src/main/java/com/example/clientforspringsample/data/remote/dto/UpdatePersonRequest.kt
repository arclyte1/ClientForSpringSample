package com.example.clientforspringsample.data.remote.dto

data class UpdatePersonRequest(
    val name: String,
    val email: String,
    val age: Int,
)