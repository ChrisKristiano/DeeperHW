package com.example.deeperhw.domain.repository

import com.example.deeperhw.data.entities.ScanEntity
import org.json.JSONObject

interface DataRepository {

    suspend fun getScannedLocations(): List<ScanEntity>

    suspend fun getScanDetails(scanId: Int): JSONObject
}