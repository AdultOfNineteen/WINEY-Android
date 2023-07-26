package com.teamwiney.core_design_system.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.teamwiney.core_design_system.R
import com.teamwiney.core_design_system.theme.Background_1

@Composable
fun SplashBackground(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background_1)
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_splash_winey),
            contentDescription = null,
            modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.TopStart)
        )
        content()
    }
}