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
class RickAndMortyViewModel @Inject constructor(private val repository: RickAndMortyRepository)
    : ViewModel(){
    private val _characters = MutableStateFlow<State>(State.Loading)
    val characters: StateFlow<State> = _characters

    init {
        fetchCharacters()
    }

    private fun fetchCharacters() {
        viewModelScope.launch {
            when (val response = repository.fetchCharacters()) {
                is NetworkRequestHandler.Success -> {
                    val charactersList = response.data.results
                    _characters.value = State.Success(charactersList)
                }
                is NetworkRequestHandler.Error -> {
                    _characters.value = State.Error(response.exception)
                }
                is NetworkRequestHandler.Failure -> {
                    _characters.value = State.Error(response.exception)
                }
            }
        }
    }
}

sealed class State {
    data class Success(val characters: List<Character>) : State()
    data class Error(val exception: Throwable) : State()
    data object Loading : State()
}