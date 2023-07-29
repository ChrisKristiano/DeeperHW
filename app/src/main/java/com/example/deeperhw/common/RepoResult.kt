package com.example.deeperhw.common

sealed class RepoResult<T>(val data: T? = null) {
    class Loading<T>: RepoResult<T>()
    class Success<T>(data: T? = null): RepoResult<T>(data)
    class Error<T>: RepoResult<T>()
}
