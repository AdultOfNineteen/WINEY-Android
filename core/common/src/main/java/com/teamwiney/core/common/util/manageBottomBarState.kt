package com.teamwiney.core.common.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import com.teamwiney.core.common.WineyAppState

@Composable
fun ManageBottomBarState(appState: WineyAppState) {
    DisposableEffect(key1 = Unit) {
        appState.updateIsMapDetail(true)
        onDispose {
            appState.updateIsMapDetail(false)
        }
    }
}
