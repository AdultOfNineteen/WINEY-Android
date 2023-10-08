package com.teamwiney.notecollection

import androidx.compose.ui.graphics.Color
import androidx.navigation.NavOptions
import com.teamwiney.core.common.base.UiEffect
import com.teamwiney.core.common.base.UiEvent
import com.teamwiney.core.common.base.UiState
import com.teamwiney.core.design.R
import com.teamwiney.data.network.model.response.WineCountry
import com.teamwiney.data.network.model.response.WineType
import com.teamwiney.ui.components.CardProperties

class NoteContract {

    data class State(
        val isLoading: Boolean = false,
        val wineList: List<CardProperties> = listOf(
            CardProperties(
                "RED",
                R.drawable.ic_red_wine,
                Color(0xFFA87575),
                listOf(Color(0xFFBF3636), Color(0xFF8F034F)),
                Color(0xFF640D0D),
                Color(0xFF441010)
            ), CardProperties(
                "WHITE",
                R.drawable.ic_white_wine,
                Color(0xFFC1BA9E),
                listOf(Color(0xFFAEAB99), Color(0xFF754A09)),
                Color(0xFF898472),
                Color(0xFF7A706D)
            ), CardProperties(
                "ROSE",
                R.drawable.ic_rose_wine,
                Color(0xFFC9A4A1),
                listOf(Color(0xFFAA678F), Color(0xFFD29263)),
                Color(0xFFBA7A71),
                Color(0xFF8F6C64)
            ), CardProperties(
                "SPARKL",
                R.drawable.ic_sparkl_wine,
                Color(0xFFA78093),
                listOf(Color(0xFF827D6B), Color(0xFFBAC59C)),
                Color(0xFF777151),
                Color(0xFF4F5144)
            ), CardProperties(
                "PORT",
                R.drawable.ic_port_wine,
                Color(0xFFB09A86),
                listOf(Color(0xFF4A2401), Color(0xFF77503A)),
                Color(0xFF4F3F28),
                Color(0xFF3A2F2F)
            ), CardProperties(
                "ETC",
                R.drawable.ic_etc_wine,
                Color(0xFF768169),
                listOf(Color(0xFF3C3D12), Color(0xFF465C18)),
                Color(0xFF2D4328),
                Color(0xFF233124)
            )

        ),
        val sortedGroup: List<String> = listOf("최신순", "인기순", "가격순"),
        val selectedSort: String = "최신순",
        val typeFilter: List<WineType> = emptyList(),
        val selectedTypeFilter: List<WineType> = emptyList(),
        val countryFilter: List<WineCountry> = emptyList(),
        val selectedCountryFilter: List<WineCountry> = emptyList(),
    ) : UiState

    sealed class Event : UiEvent {
        object ShowFilters : Event()
    }

    sealed class Effect : UiEffect {
        data class NavigateTo(
            val destination: String,
            val navOptions: NavOptions? = null
        ) : Effect()

        data class ShowSnackBar(val message: String) : Effect()
    }

}