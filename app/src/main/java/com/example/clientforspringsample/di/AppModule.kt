package com.example.clientforspringsample.di

import com.example.clientforspringsample.common.Constants
import com.example.clientforspringsample.data.remote.PersonApi
import com.example.clientforspringsample.data.repository.PersonRepositoryImpl
import com.example.clientforspringsample.domain.model.Person
import com.example.clientforspringsample.domain.repository.PersonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providePersonApi(): PersonApi = Retrofit
        .Builder()
        .baseUrl(Constants.PERSONS_API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PersonApi::class.java)

    @Provides
    @Singleton
    fun providePersonRepository(
        api: PersonApi
    ): PersonRepository {
        return PersonRepositoryImpl(api)
    }
}