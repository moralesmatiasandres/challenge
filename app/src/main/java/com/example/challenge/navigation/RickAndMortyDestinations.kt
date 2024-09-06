package com.example.challenge.navigation

object RickAndMortyDestinations {
    const val CHARACTER_LIST = "character_list"
    const val CHARACTER_DETAIL = "character_detail/{characterId}"

    fun characterDetailRoute(characterId: Int): String {
        return "character_detail/$characterId"
    }
}