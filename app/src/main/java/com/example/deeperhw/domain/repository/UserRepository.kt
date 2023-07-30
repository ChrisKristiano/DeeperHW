package com.example.deeperhw.domain.repository

interface UserRepository {

    suspend fun login(email: String, password: String)
}