package com.teamwiney.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.teamwiney.ui.theme.WineyTheme

@Composable
fun LoadingDialog() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = WineyTheme.colors.background_1),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = WineyTheme.colors.main_1
        )
    }
}