package com.example.challenge.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.challenge.network.responses.Character
import com.example.challenge.viewModels.RickAndMortyViewModel
import com.example.challenge.viewModels.State

@Composable
fun CharacterDetailScreen(viewModel: RickAndMortyViewModel, characterId: Int) {
    val charactersState by viewModel.characters.collectAsState()
    val character = (charactersState as? State.Success)?.characters?.firstOrNull {
        it.id == characterId
    }

    character?.let {
        CharacterDetailContent(character = it)
    } ?: Text("Character not found")
}


@Composable
fun CharacterDetailContent(character: Character) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CustomImage(
            imageUrl = character.image,
            contentDescription = character.name,
            size = 200.dp,
            clipShape = RoundedCornerShape(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = character.name, style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Status: ${character.status}", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Gender: ${character.gender}", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Species: ${character.species}", style = MaterialTheme.typography.bodyLarge)
    }
}

