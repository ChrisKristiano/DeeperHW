package com.example.deeperhw.presentation.util.extensions

import com.google.android.gms.maps.model.LatLng

fun LatLng.swapCoordinates(): LatLng = LatLng(this.longitude, this.latitude)