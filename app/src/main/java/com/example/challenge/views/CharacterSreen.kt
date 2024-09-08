package com.example.challenge.views
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.challenge.network.responses.Character
import com.example.challenge.viewModels.RickAndMortyViewModel
import com.example.challenge.viewModels.State


@Composable
fun CharacterList(characters: List<Character>, onCharacterClick: (Character) -> Unit) {
    LazyColumn {
        itemsIndexed(characters) { _, character ->
            CharacterItem(character = character, onClick = onCharacterClick)
        }
    }
}

@Composable
fun CharacterListScreen(viewModel: RickAndMortyViewModel, onCharacterClick: (Character) -> Unit) {
    val charactersState by viewModel.characters.collectAsState()

    when (charactersState) {
        is State.Success -> {
            CharacterList(characters = (charactersState as State.Success).characters, onCharacterClick = onCharacterClick)
        }
        is State.Empty -> {

        }
        is State.Error -> {
            Text("Error: ${(charactersState as State.Error).exception.message}")
        }
        State.Loading -> {
            CircularProgressIndicator()
        }
    }
}




