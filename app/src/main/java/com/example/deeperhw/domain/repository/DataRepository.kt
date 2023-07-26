package com.example.deeperhw.domain.repository

interface DataRepository {

    suspend fun login(email: String, password: String)
}