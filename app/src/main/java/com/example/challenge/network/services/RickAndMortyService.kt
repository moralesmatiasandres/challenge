package com.example.challenge.network.services

import com.example.challenge.network.responses.CharacterResponse
import retrofit2.Response
import retrofit2.http.GET

interface RickAndMortyService {
    @GET("character")
    suspend fun getAllCharacters(): Response<CharacterResponse>
}