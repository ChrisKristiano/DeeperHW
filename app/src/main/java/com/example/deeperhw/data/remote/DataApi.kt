package com.example.deeperhw.data.remote

import com.example.deeperhw.data.dto.LoginRequest
import com.example.deeperhw.data.dto.LoginResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface DataApi {

    @POST("login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("geoData?grid=FAST&generator=BS")
    suspend fun getScanDetails(
        @Query(value = "scanIds") scanId: String,
        @Query(value = "token") token: String
    ): Response<ResponseBody>
}