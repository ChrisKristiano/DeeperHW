package com.example.deeperhw.data.local

import com.example.deeperhw.data.entities.ScanEntity

class DataCache {

    private var scannedLocations: List<ScanEntity> = emptyList()

    fun getScannedLocations(): List<ScanEntity> = scannedLocations

    fun setScannedLocations(list: List<ScanEntity>) {
        scannedLocations = list
    }
}