package com.example.deeperhw.common

sealed class RepoResult<T>(val data: T? = null) {
    class Loading: RepoResult<Nothing>()
    class Success<T>(data: T? = null): RepoResult<T>(data)
    data class Error(val errorType: RepoResultError): RepoResult<Nothing>()
}

enum class RepoResultError {
    INVALID_AUTHENTICATION
}
