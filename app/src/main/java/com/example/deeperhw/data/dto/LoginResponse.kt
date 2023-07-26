package com.example.deeperhw.data.dto

import com.example.deeperhw.data.entities.LoginEntity
import com.example.deeperhw.data.entities.ScanEntity

data class LoginResponse(
    val login: LoginEntity = LoginEntity(),
    val scans: List<ScanEntity> = emptyList()
)
