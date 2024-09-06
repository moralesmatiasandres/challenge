package com.example.challenge.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.challenge.viewModels.RickAndMortyViewModel
import com.example.challenge.viewModels.State
import com.example.challenge.views.CharacterDetailScreen
import com.example.challenge.views.CharacterListScreen

@Composable
fun RickAndMortyNavHost(
    navController: NavHostController,
    viewModel: RickAndMortyViewModel = viewModel(),
    startDestination: String = RickAndMortyDestinations.CHARACTER_LIST
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(RickAndMortyDestinations.CHARACTER_LIST) {
            CharacterListScreen(viewModel = viewModel) { character ->
                navController.navigate(RickAndMortyDestinations.characterDetailRoute(character.id))
            }
        }
        composable(
            route = RickAndMortyDestinations.CHARACTER_DETAIL,
            arguments = listOf(navArgument("characterId") { type = NavType.IntType })
        ) { backStackEntry ->
            val characterId = backStackEntry.arguments?.getInt("characterId") ?: 0
            val charactersState by viewModel.characters.collectAsState()
            val character = (charactersState as? State.Success)?.characters?.firstOrNull { it.id == characterId }
            character?.let { CharacterDetailScreen(it) }
        }

    }
}