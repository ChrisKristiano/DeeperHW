package com.example.deeperhw.data

import com.example.deeperhw.data.dto.LoginRequest
import com.example.deeperhw.data.local.DataCache
import com.example.deeperhw.data.local.SharedPrefs
import com.example.deeperhw.data.remote.DataApi
import com.example.deeperhw.domain.repository.DataRepository
import javax.inject.Inject

class DataRepositoryImpl @Inject constructor(
    private val api: DataApi,
    private val cache: DataCache,
    private val sharedPrefs: SharedPrefs
) : DataRepository {

    override suspend fun login(email: String, password: String) {
        val request = LoginRequest(
            email = email,
            password = password
        )
        val result = api.login(request)
        sharedPrefs.setToken(result.login.token.orEmpty())
        cache.setScannedLocations(result.scans)
    }
}