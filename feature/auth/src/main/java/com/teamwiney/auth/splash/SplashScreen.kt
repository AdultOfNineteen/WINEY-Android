package com.teamwiney.auth.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamwiney.core.design.R
import com.teamwiney.ui.splash.SplashBackground
import kotlinx.coroutines.delay

@Preview(
    showBackground = true,
    showSystemUi = true,
    backgroundColor = 0xFF1F2126
)
@Composable
fun SplashScreen(onCompleted: () -> Unit = { }) {
    LaunchedEffect(true) {
        delay(1000)
        onCompleted()
    }

    SplashBackground {
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.Center)
        ) {
            Image(
                painter = painterResource(id = R.mipmap.img_winey_logo),
                contentDescription = null,
                modifier = Modifier.size(74.dp, 68.dp)
            )
            Image(
                painter = painterResource(id = R.mipmap.img_winey_logo_title),
                contentDescription = null,
                modifier = Modifier.size(128.dp, 36.dp)
            )
        }
    }
}