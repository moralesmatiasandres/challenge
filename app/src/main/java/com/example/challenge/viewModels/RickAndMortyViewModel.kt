package com.example.challenge.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge.network.util.NetworkRequestHandler
import com.example.challenge.repositories.RickAndMortyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.challenge.network.responses.Character
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class RickAndMortyViewModel @Inject constructor(private val repository: RickAndMortyRepository) : ViewModel(){
    private val _characters = MutableStateFlow<State<List<Character>>>(State.Loading)
    val characters: StateFlow<State<List<Character>>> = _characters

    init {
        fetchCharacters()
    }

    private fun fetchCharacters() {
        viewModelScope.launch {
            when (val response = repository.fetchCharacters()) {
                is NetworkRequestHandler.Success -> {
                    val charactersList = response.data.results  // Accede a la lista de personajes
                    _characters.value = State.Success(charactersList)
                }
                is NetworkRequestHandler.Error -> _characters.value = State.Error(response.exception)
                is NetworkRequestHandler.Failure -> _characters.value = State.Error(response.exception)
            }
        }
    }
}

sealed class State<out T> {
    data class Success<out T>(val data: T) : State<T>()
    data class Error(val exception: Throwable) : State<Nothing>()
    object Loading : State<Nothing>()
}