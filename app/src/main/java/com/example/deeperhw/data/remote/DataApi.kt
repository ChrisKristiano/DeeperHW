package com.example.deeperhw.data.remote

import com.example.deeperhw.data.dto.LoginRequest
import com.example.deeperhw.data.dto.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface DataApi {

    @POST("login/")
    suspend fun login(@Body request: LoginRequest): LoginResponse
}