package com.example.miquido.data.networking


import com.example.miquido.domain.util.NetworkError
import com.example.miquido.domain.util.Result
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import okio.IOException
import retrofit2.HttpException
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> safeCall(
    crossinline execute: suspend () -> T
): Result<T, NetworkError> {
    return try {
        val response = execute()
        Result.Success(response)
    } catch (e: IOException) {
        Result.Error(NetworkError.NO_INTERNET)
    } catch (e: HttpException) {
        when (e.code()) {
            408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
            429 -> Result.Error(NetworkError.TOO_MANY_REQUESTS)
            in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
            else -> Result.Error(NetworkError.UNKNOWN)
        }
    } catch (e: SerializationException) {
        Result.Error(NetworkError.SERIALIZATION)
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        Result.Error(NetworkError.UNKNOWN)
    }
}