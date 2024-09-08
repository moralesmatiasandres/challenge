package com.example.challenge.models

import com.example.challenge.network.responses.Character
import com.example.challenge.network.responses.CharacterResponse
import com.example.challenge.network.responses.Info
import com.example.challenge.network.responses.Location

class CharacterResponseMockModel {
    companion object {
        // Mock de Info
        val mockInfo = Info(
            count = 826,
            pages = 42,
            next = "https://rickandmortyapi.com/api/character/?page=2",
            prev = null
        )

        // Mock de Location
        val mockLocation = Location(
            name = "Earth",
            url = "https://rickandmortyapi.com/api/location/1"
        )

        // Mock de Character
        val mockCharacter = Character(
            id = 1,
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            origin = mockLocation,
            location = mockLocation,
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            episode = listOf(
                "https://rickandmortyapi.com/api/episode/1",
                "https://rickandmortyapi.com/api/episode/2"
            ),
            url = "https://rickandmortyapi.com/api/character/1",
            created = "2017-11-04T18:48:46.250Z"
        )

        // Mock de CharacterResponse con datos válidos
        val mockCharacterResponse = CharacterResponse(
            info = mockInfo,
            results = listOf(mockCharacter)
        )

        // Mock de CharacterResponse con datos vacíos
        val mockEmptyCharacterResponse = CharacterResponse(
            info = mockInfo.copy(count = 0, pages = 0, next = null, prev = null),
            results = emptyList()
        )

        val simulatedCharacters = listOf(
            mockCharacter
        )

    }
}