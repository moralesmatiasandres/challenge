package com.example.challenge.repositories

import com.example.challenge.models.CharacterResponseMockModel
import com.example.challenge.network.responses.CharacterResponse
import com.example.challenge.network.services.RickAndMortyService
import com.example.challenge.network.util.NetworkResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.io.IOException


class RickAndMortyRepositoryTest {
    private val service : RickAndMortyService = mockk()
    private val repository = RickAndMortyRepository(service)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `fetchCharacters returns success with valid data`() = runTest {
        // Arrange
        val characterResponse = CharacterResponseMockModel.mockSuccessResponse()
        coEvery { service.getAllCharacters() } returns Response.success(characterResponse)

        // Act
        val result = repository.fetchCharacters()

        // Assert
        assertTrue(result is NetworkResponse.Success)
        assertEquals(characterResponse, (result as NetworkResponse.Success).data)
    }

    @Test
    fun `fetchCharacters returns success with empty data`() = runTest {
        // Arrange
        val emptyResponse = CharacterResponseMockModel.mockEmptyResponse()
        coEvery { service.getAllCharacters() } returns Response.success(emptyResponse)

        // Act
        val result = repository.fetchCharacters()

        // Assert
        assertTrue(result is NetworkResponse.Success)
        assertEquals(emptyResponse, (result as NetworkResponse.Success).data)
    }

    @Test
    fun `fetchCharacters returns Error when API call returns 404`() = runTest {
        // Arrange
        val errorResponseBody = ResponseBody.create(null, "Not Found")
        coEvery { service.getAllCharacters() } returns Response.error(404, errorResponseBody)

        // Act
        val result = repository.fetchCharacters()

        // Assert
        assertTrue(result is NetworkResponse.Error)
        assertEquals("Not Found", (result as NetworkResponse.Error).exception.message)
    }

    @Test
    fun `fetchCharacters returns Error when API call returns 500`() = runTest {
        // Arrange
        val errorResponseBody = ResponseBody.create(null, "Server Error")
        coEvery { service.getAllCharacters() } returns Response.error(500, errorResponseBody)

        // Act
        val result = repository.fetchCharacters()

        // Assert
        assertTrue(result is NetworkResponse.Error)
        assertEquals("Server Error", (result as NetworkResponse.Error).exception.message)
    }

    @Test
    fun `fetchCharacters returns Failure when API call throws an exception`() = runTest {
        // Arrange
        coEvery { service.getAllCharacters() } throws IOException("Network Error")

        // Act
        val result = repository.fetchCharacters()

        // Assert
        assertTrue(result is NetworkResponse.Failure)
        assertEquals("Network Error", (result as NetworkResponse.Failure).exception.message)
    }

}