package com.teamwiney.core_design_system.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

class WineyColors(
    main_1: Color = Color(0xFF5123DF),
    main_2: Color = Color(0xFF774BFF),
    main_3: Color = Color(0xFFACBFFE),
    point_1: Color = Color(0xFF4B2EFF),
    point_2: Color = Color.White,
    error: Color = Color(0xFFFF2633),
    background_1: Color = Color(0xFF1F2126),
    gray_50: Color = Color(0xFFFFFFFF),
    gray_100: Color = Color(0xFFFAFAFA),
    gray_200: Color = Color(0xFFF8F8F8),
    gray_300: Color = Color(0xFFF0F0F0),
    gray_400: Color = Color(0xFFDEDEDE),
    gray_500: Color = Color(0xFFBDBFC1),
    gray_600: Color = Color(0xFF909397),
    gray_700: Color = Color(0xFF7B7F83),
    gray_800: Color = Color(0xFF63666A),
    gray_900: Color = Color(0xFF3A3D40),
    gray_950: Color = Color(0xFF262525),
    black: Color = Color(0xFF121212)
) {
    var main_1 by mutableStateOf(main_1)
        private set
    var main_2 by mutableStateOf(main_2)
        private set
    var main_3 by mutableStateOf(main_3)
        private set

    var point_1 by mutableStateOf(point_1)
        private set
    var point_2 by mutableStateOf(point_2)
        private set

    var error by mutableStateOf(error)
        private set

    var background_1 by mutableStateOf(background_1)
        private set

    var gray_50 by mutableStateOf(gray_50)
        private set
    var gray_100 by mutableStateOf(gray_100)
        private set
    var gray_200 by mutableStateOf(gray_200)
        private set
    var gray_300 by mutableStateOf(gray_300)
        private set
    var gray_400 by mutableStateOf(gray_400)
        private set
    var gray_500 by mutableStateOf(gray_500)
        private set
    var gray_600 by mutableStateOf(gray_600)
        private set
    var gray_700 by mutableStateOf(gray_700)
        private set
    var gray_800 by mutableStateOf(gray_800)
        private set
    var gray_900 by mutableStateOf(gray_900)
        private set
    var gray_950 by mutableStateOf(gray_950)
        private set

    var black by mutableStateOf(black)
        private set

    fun copy(
        main_1: Color = this.main_1,
        main_2: Color = this.main_2,
        main_3: Color = this.main_3,
        point_1: Color = this.point_1,
        point_2: Color = this.point_2,
        error: Color = this.error,
        background_1: Color = this.background_1,
        gray_50: Color = this.gray_50,
        gray_100: Color = this.gray_100,
        gray_200: Color = this.gray_200,
        gray_300: Color = this.gray_300,
        gray_400: Color = this.gray_400,
        gray_500: Color = this.gray_500,
        gray_600: Color = this.gray_600,
        gray_700: Color = this.gray_700,
        gray_800: Color = this.gray_800,
        gray_900: Color = this.gray_900,
        gray_950: Color = this.gray_950,
        black: Color = this.black
    ) = WineyColors(
        main_1 = main_1,
        main_2 = main_2,
        main_3 = main_3,
        point_1 = point_1,
        point_2 = point_2,
        error = error,
        background_1 = background_1,
        gray_50 = gray_50,
        gray_100 = gray_100,
        gray_200 = gray_200,
        gray_300 = gray_300,
        gray_400 = gray_400,
        gray_500 = gray_500,
        gray_600 = gray_600,
        gray_700 = gray_700,
        gray_800 = gray_800,
        gray_900 = gray_900,
        gray_950 = gray_950,
        black = black
    )

    fun updateColorFrom(other: WineyColors) {
        main_1 = other.main_1
        main_2 = other.main_2
        main_3 = other.main_3
        point_1 = other.point_1
        point_2 = other.point_2
        error = other.error
        background_1 = other.background_1
        gray_50 = other.gray_50
        gray_100 = other.gray_100
        gray_200 = other.gray_200
        gray_300 = other.gray_300
        gray_400 = other.gray_400
        gray_500 = other.gray_500
        gray_600 = other.gray_600
        gray_700 = other.gray_700
        gray_800 = other.gray_800
        gray_900 = other.gray_900
        gray_950 = other.gray_950
        black = other.black
    }
}

val LocalColors = staticCompositionLocalOf { wineyColors() }
