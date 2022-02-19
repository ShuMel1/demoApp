package com.example.currencyexchangeapp.domain.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

/**
 * helper method for safe api calls which handles exceptions
 */
inline fun <reified RESPONSE, RESULT> safeApiCall(
    mapper: Mapper<RESPONSE, RESULT>,
    crossinline apiCall: suspend () -> Response<RESPONSE>,
    crossinline onFetchFailed: (Throwable) -> Unit = {},
    convertingKey: String
): Flow<Resource<RESULT>> = flow {
    emit(Resource.Loading(null))
    try {
        val response = apiCall.invoke()
        if (response.isSuccessful) {
            response.body()?.let {
                emit(Resource.Success(mapper.map(it, convertingKey)))
            } ?: emit(Resource.Error(null, Exception(response.message()), response.code()))
        } else {
            emit(Resource.Error(null, Exception(response.message()), response.code()))
            onFetchFailed.invoke(Exception(response.message()))
        }
    } catch (e: Exception) {
        emit(Resource.Error(null, e))
        onFetchFailed.invoke(e)
    }
}

inline fun <reified RESPONSE> safeApiCall(
    crossinline apiCall: () -> Response<RESPONSE>,
    crossinline onFetchFailed: (Throwable) -> Unit = {},
): Flow<Resource<RESPONSE>> = flow {
    emit(Resource.Loading(null))
    try {
        val response = apiCall.invoke()
        if (response.isSuccessful) {
            response.body()?.let {
                emit(Resource.Success(it))
            } ?: emit(Resource.Error(null, Exception(response.message()), response.code()))
        } else {
            emit(Resource.Error(null, Exception(response.message()), response.code()))
            onFetchFailed.invoke(Exception(response.message()))
        }
    } catch (e: Exception) {
        emit(Resource.Error(null, e))
        onFetchFailed.invoke(e)
    }
}

