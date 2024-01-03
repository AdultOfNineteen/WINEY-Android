package com.teamwiney.map.manager

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
internal fun manageSystemUIColor(filter: String) {
    val systemUiController = rememberSystemUiController()
    LaunchedEffect(key1 = filter) {
        if (filter == "전체") {
            systemUiController.setSystemBarsColor(
                color = Color.Transparent
            )
        } else {
            systemUiController.setSystemBarsColor(
                color = Color(0xB31F2126)
            )
        }
    }
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