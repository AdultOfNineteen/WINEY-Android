package com.teamwiney.map.manager

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
internal fun manageSystemUIColor() {
    val systemUiController = rememberSystemUiController()
    DisposableEffect(key1 = Unit) {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent
        )
        onDispose {
            systemUiController.setSystemBarsColor(
                color = Color(0xFF1F2126)
            )
        }
    }
}