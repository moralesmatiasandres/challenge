package com.example.challenge.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.challenge.models.CharacterResponseMockModel
import com.example.challenge.network.util.NetworkResponse
import com.example.challenge.repositories.RickAndMortyRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class RickAndMortyViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)
    private val repository = mockk<RickAndMortyRepository>()
    private lateinit var viewModel: RickAndMortyViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = RickAndMortyViewModel(repository)

        // Configura la respuesta simulada para el método fetchCharacters
        coEvery { repository.fetchCharacters() } returns NetworkResponse.Success(CharacterResponseMockModel.mockEmptyCharacterResponse)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should be Loading`() = testScope.runTest {
        // Act
        val state = viewModel.characters.first()

        // Assert
        assertEquals(State.Loading, state)
    }

}