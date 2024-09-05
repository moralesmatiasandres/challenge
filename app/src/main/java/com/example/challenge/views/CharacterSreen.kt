package com.example.challenge.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.challenge.network.responses.Character
import com.example.challenge.viewModels.RickAndMortyViewModel
import com.example.challenge.viewModels.State

@Composable
fun CharacterItem(character: Character) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberImagePainter(character.image),
            contentDescription = character.name,
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = character.name, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun CharacterListScreen(viewModel: RickAndMortyViewModel = viewModel()) {
    val charactersState by viewModel.characters.collectAsState()

    when (charactersState) {
        is State.Success -> {
            val characters = (charactersState as State.Success<List<Character>>).data
            LazyColumn {
                itemsIndexed(characters) { _, character ->
                    CharacterItem(character)
                }
            }
        }
        is State.Error -> {
            Text("Error: ${(charactersState as State.Error).exception.message}")
        }
        State.Loading -> {
            CircularProgressIndicator()
        }
    }
}
