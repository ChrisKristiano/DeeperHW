package com.example.deeperhw.data

import com.example.deeperhw.data.entities.ScanEntity
import com.example.deeperhw.data.local.DataCache
import com.example.deeperhw.data.local.SharedPrefs
import com.example.deeperhw.data.remote.Api
import com.example.deeperhw.domain.repository.DataRepository
import org.json.JSONObject
import java.lang.Exception
import javax.inject.Inject

class DataRepositoryImpl @Inject constructor(
    private val api: Api,
    private val cache: DataCache,
    private val sharedPrefs: SharedPrefs
) : DataRepository {

    override suspend fun getScannedLocations(): List<ScanEntity> = cache.getScannedLocations()

    override suspend fun getScanDetails(scanId: Int): JSONObject {
        val result = api.getScanDetails(
            scanId = scanId.toString(),
            token = sharedPrefs.getToken()
        )
        return result.body()?.let {
            JSONObject(it.string())
        } ?: throw Exception()
    }
}