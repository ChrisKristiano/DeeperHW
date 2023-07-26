package com.example.deeperhw.data.dto

import com.example.deeperhw.data.entities.LoginEntity

data class LoginResponse(
    val login: LoginEntity = LoginEntity()
)
