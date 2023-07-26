package com.example.deeperhw.common

sealed class RepoResult<T>(val data: T? = null) {
    class Loading: RepoResult<Nothing>()
    class Success<T>(data: T? = null): RepoResult<T>(data)
    class Error: RepoResult<Nothing>()
}
