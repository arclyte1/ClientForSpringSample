package com.example.clientforspringsample.data.remote

import com.example.clientforspringsample.data.remote.dto.CreatePersonRequest
import com.example.clientforspringsample.data.remote.dto.PersonDto
import com.example.clientforspringsample.data.remote.dto.UpdatePersonRequest
import com.example.clientforspringsample.domain.model.Person
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PersonApi {

    @GET(".")
    suspend fun getAll(): List<PersonDto>

    @POST(".")
    suspend fun create(
        @Body request: CreatePersonRequest
    ): PersonDto

    @PUT("{id}")
    suspend fun update(
        @Path("id") id: Long,
        @Body request: UpdatePersonRequest,
    ): PersonDto

    @DELETE("{id}")
    suspend fun delete(
        @Path("id") id: Long
    )
}