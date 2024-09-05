package com.example.challenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.challenge.ui.theme.ChallengeTheme
import com.example.challenge.viewModels.RickAndMortyViewModel
import com.example.challenge.viewModels.State
import com.example.challenge.views.CharacterDetailScreen
import com.example.challenge.views.CharacterListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChallengeTheme {
                val navController = rememberNavController()
                RickAndMortyNavHost(navController = navController)
            }
        }
    }
}

@Composable
fun RickAndMortyNavHost(
    navController: NavHostController,
    viewModel: RickAndMortyViewModel = viewModel(), // Inyectamos el ViewModel
    startDestination: String = "character_list"
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable("character_list") {
            CharacterListScreen(viewModel = viewModel) { character ->
                navController.navigate("character_detail/${character.id}")
            }
        }
        composable(
            "character_detail/{characterId}",
            arguments = listOf(navArgument("characterId") { type = NavType.IntType })
        ) { backStackEntry ->
            // Obtener el ID del personaje desde los argumentos
            val characterId = backStackEntry.arguments?.getInt("characterId") ?: 0
            // Usar collectAsState para observar el flujo de personajes
            val charactersState by viewModel.characters.collectAsState()

            val character = (charactersState as? State.Success)?.data?.firstOrNull { it.id == characterId }

            character?.let { CharacterDetailScreen(it) }
        }

    }
}


