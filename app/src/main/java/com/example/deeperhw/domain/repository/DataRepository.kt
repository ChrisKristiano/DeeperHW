package com.example.deeperhw.domain.repository

import com.example.deeperhw.data.entities.ScanEntity

interface DataRepository {

    suspend fun login(email: String, password: String)

    suspend fun getScannedLocations(): List<ScanEntity>
}