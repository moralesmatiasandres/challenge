package com.example.challenge.network.util

import com.example.challenge.network.util.NetworkRequestHandler.executeRequest
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Test
import retrofit2.Response

class NetworkResponseKtTest {
    // Caso: API call exitosa
    @Test
    fun `executeRequest returns Success when API call is successful`() = runTest {
        // Arrange
        val apiCall = mockk<suspend () -> Response<String>>()
        coEvery { apiCall.invoke() } returns Response.success("Success response")

        // Act
        val result = executeRequest(apiCall)

        // Assert
        assertTrue(result is NetworkResponse.Success)
        assertEquals("Success response", (result as NetworkResponse.Success).data)
    }

    // Caso: API call no exitosa
    @Test
    fun `executeRequest returns Error when API call is unsuccessful`() = runTest {
        // Arrange
        val apiCall = mockk<suspend () -> Response<String>>()
        val errorResponseBody = ResponseBody.create(null, "Error")
        coEvery { apiCall.invoke() } returns Response.error(404, errorResponseBody)

        // Act
        val result = executeRequest(apiCall)

        // Assert
        assertTrue(result is NetworkResponse.Error)
        assertEquals("Error", (result as NetworkResponse.Error).exception.message)
    }

    // Caso: La API lanza una excepción
    @Test
    fun `executeRequest returns Failure when API call throws an exception`() = runTest {
        // Arrange
        val apiCall = mockk<suspend () -> Response<String>>()
        coEvery { apiCall.invoke() } throws RuntimeException("Exception occurred")

        // Act
        val result = executeRequest(apiCall)

        // Assert
        assertTrue(result is NetworkResponse.Failure)
        assertEquals("Exception occurred", (result as NetworkResponse.Failure).exception.message)
    }

    // Caso: API call devuelve 500 (Internal Server Error)
    @Test
    fun `executeRequest returns Error when API call returns 500`() = runTest {
        // Arrange
        val apiCall = mockk<suspend () -> Response<String>>()
        val errorResponseBody = ResponseBody.create(null, "Internal Server Error")
        coEvery { apiCall.invoke() } returns Response.error(500, errorResponseBody)

        // Act
        val result = executeRequest(apiCall)

        // Assert
        assertTrue(result is NetworkResponse.Error)
        assertEquals("Internal Server Error", (result as NetworkResponse.Error).exception.message)
    }

    // Caso: API call devuelve 404
    @Test
    fun `executeRequest returns Error when API call returns 404`() = runTest {
        // Arrange
        val apiCall = mockk<suspend () -> Response<String>>()
        val errorResponseBody = ResponseBody.create(null, "Not Found")
        coEvery { apiCall.invoke() } returns Response.error(404, errorResponseBody)

        // Act
        val result = executeRequest(apiCall)

        // Assert
        assertTrue(result is NetworkResponse.Error)
        assertEquals("Not Found", (result as NetworkResponse.Error).exception.message)
    }

    // Caso: API call devuelve 400 (Bad Request)
    @Test
    fun `executeRequest returns Error when API call returns 400`() = runTest {
        // Arrange
        val apiCall = mockk<suspend () -> Response<String>>()
        val errorResponseBody = ResponseBody.create(null, "Bad Request")
        coEvery { apiCall.invoke() } returns Response.error(400, errorResponseBody)

        // Act
        val result = executeRequest(apiCall)

        // Assert
        assertTrue(result is NetworkResponse.Error)
        assertEquals("Bad Request", (result as NetworkResponse.Error).exception.message)
    }

    @Test
    fun `executeRequest handles empty response body`() = runTest {
        // Arrange
        val apiCall = mockk<suspend () -> Response<String>>()
        coEvery { apiCall.invoke() } returns Response.success("")

        // Act
        val result = executeRequest(apiCall)

        // Assert
        assertTrue(result is NetworkResponse.Success)
        assertEquals("", (result as NetworkResponse.Success).data)
    }
}