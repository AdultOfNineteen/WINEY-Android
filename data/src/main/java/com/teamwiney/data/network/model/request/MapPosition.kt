package com.teamwiney.data.network.model.request

data class MapPosition(
    val latitude: Double,
    val leftTopLatitude: Double,
    val leftTopLongitude: Double,
    val longitude: Double,
    val rightBottomLatitude: Double,
    val rightBottomLongitude: Double
)