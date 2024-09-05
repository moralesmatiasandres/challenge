package com.example.challenge.repositories

import com.example.challenge.network.responses.CharacterResponse
import com.example.challenge.network.services.RickAndMortyService
import com.example.challenge.network.util.NetworkRequestHandler
import com.example.challenge.network.util.executeRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RickAndMortyRepository @Inject constructor(private val service : RickAndMortyService) {
    suspend fun fetchCharacters() : NetworkRequestHandler<CharacterResponse> {
        return withContext(Dispatchers.IO) {
            executeRequest { service.getAllCharacters() }
        }
    }
}