package com.example.challenge.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.challenge.models.CharacterResponseMockModel
import com.example.challenge.network.util.NetworkResponse
import com.example.challenge.repositories.RickAndMortyRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
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
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchCharacters returns Empty when no characters`() = testScope.runTest {
        // Configura la respuesta simulada para el método fetchCharacters
        coEvery { repository.fetchCharacters() } returns NetworkResponse.Success(CharacterResponseMockModel.mockEmptyResponse())

        // Act
        viewModel = RickAndMortyViewModel(repository)

        // Avanza el dispatcher hasta que todas las corutinas se completen
        testDispatcher.scheduler.advanceUntilIdle()

        // Obtén el estado del ViewModel
        val state = viewModel.characters.first()

        // Assert
        assertEquals(State.Empty, state)
    }

    @Test
    fun `fetchCharacters returns Success with characters`() = testScope.runTest {
        // Configura la respuesta simulada para el método fetchCharacters
        coEvery { repository.fetchCharacters() } returns NetworkResponse.Success(CharacterResponseMockModel.mockSuccessResponse())

        // Act
        viewModel = RickAndMortyViewModel(repository)  // Recreate ViewModel to apply new mock configuration
        testDispatcher.scheduler.advanceUntilIdle()

        // Obtén el estado del ViewModel
        val state = viewModel.characters.first()

        // Assert
        assertTrue(state is State.Success)
        assertEquals(1, (state as State.Success).characters.size)
        assertEquals("Rick Sanchez", state.characters[0].name)
    }

    @Test
    fun `fetchCharacters returns Error when repository fails`() = testScope.runTest {
        // Configura la respuesta simulada para el método fetchCharacters
        coEvery { repository.fetchCharacters() } returns NetworkResponse.Error(CharacterResponseMockModel.mockErrorResponse())

        // Act
        viewModel = RickAndMortyViewModel(repository)  // Recreate ViewModel to apply new mock configuration
        testDispatcher.scheduler.advanceUntilIdle()

        // Obtén el estado del ViewModel
        val state = viewModel.characters.first()

        // Assert
        assertTrue(state is State.Error)
        assertEquals("Network Error", (state as State.Error).exception.message)
    }
}
