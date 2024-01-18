package com.teamwiney.data.network.model.response

data class WineShop(
    val address: String,
    val businessHour: String,
    val imgUrl: String,
    val latitude: Double,
    val like: Boolean,
    val longitude: Double,
    val meter: Double,
    val name: String,
    val phone: String,
    val shopId: Int,
    val shopMoods: List<String>,
    val shopType: String
)