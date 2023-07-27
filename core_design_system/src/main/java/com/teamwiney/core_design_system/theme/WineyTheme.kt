package com.teamwiney.core_design_system.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

object WineyTheme {
    val colors: WineyColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val typography: WineyTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current
}