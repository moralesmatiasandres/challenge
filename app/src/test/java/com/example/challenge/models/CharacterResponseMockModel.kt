package com.example.challenge.models

import com.example.challenge.network.responses.Character
import com.example.challenge.network.responses.CharacterResponse
import com.example.challenge.network.responses.Info
import com.example.challenge.network.responses.Location

object CharacterResponseMockModel {
    fun mockSuccessResponse(): CharacterResponse {
        return CharacterResponse(
            info = Info(
                count = 826,
                pages = 42,
                next = "https://rickandmortyapi.com/api/character/?page=2",
                prev = null
            ),
            results = listOf(
                Character(
                    id = 1,
                    name = "Rick Sanchez",
                    status = "Alive",
                    species = "Human",
                    type = "",
                    gender = "Male",
                    origin = Location(name = "Earth", url = "https://rickandmortyapi.com/api/location/1"),
                    location = Location(name = "Earth", url = "https://rickandmortyapi.com/api/location/20"),
                    image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                    episode = listOf(
                        "https://rickandmortyapi.com/api/episode/1",
                        "https://rickandmortyapi.com/api/episode/2"
                    ),
                    url = "https://rickandmortyapi.com/api/character/1",
                    created = "2017-11-04T18:48:46.250Z"
                )
            )
        )
    }

    fun mockEmptyResponse(): CharacterResponse {
        return CharacterResponse(
            info = Info(
                count = 0,
                pages = 0,
                next = null,
                prev = null
            ),
            results = emptyList()
        )
    }

    fun mockErrorResponse(): Throwable {
        return Throwable("Network Error")
    }
}