package com.teamwiney.mypage.badge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.ui.components.TopBar
import com.teamwiney.ui.theme.WineyTheme

@Composable
fun MyPageBadgeScreen(
    appState: WineyAppState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WineyTheme.colors.background_1)
    ) {
        TopBar(
            content = "WINEY 뱃지"
        ) {
            appState.navController.navigateUp()
        }

    }
}