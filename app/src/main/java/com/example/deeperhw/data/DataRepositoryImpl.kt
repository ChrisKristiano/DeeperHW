package com.example.deeperhw.data

import com.example.deeperhw.data.dto.LoginRequest
import com.example.deeperhw.data.remote.DataApi
import com.example.deeperhw.domain.repository.DataRepository
import javax.inject.Inject

class DataRepositoryImpl @Inject constructor(
    private val api: DataApi
) : DataRepository {

    override suspend fun login(email: String, password: String) {
        val request = LoginRequest(
            email = email,
            password = password
        )
        api.login(request)
    }
}