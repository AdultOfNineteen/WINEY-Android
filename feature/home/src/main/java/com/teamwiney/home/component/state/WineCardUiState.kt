package com.teamwiney.home.component.state

import com.teamwiney.ui.components.WineColor

data class WineCardUiState(
    val wineColor: WineColor,
    val name: String,
    val country: String,
    val varietal: String,
    val price: Int
)