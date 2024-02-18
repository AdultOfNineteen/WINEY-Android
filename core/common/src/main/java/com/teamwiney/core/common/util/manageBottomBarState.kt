package com.teamwiney.core.common.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import com.teamwiney.core.common.WineyAppState

@Composable
fun manageBottomBarState(appState: WineyAppState) {
    DisposableEffect(key1 = Unit) {
        appState.updateBottomBarVisibility(false)
        onDispose {
            appState.updateBottomBarVisibility(true)
        }
    }
}
