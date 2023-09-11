package com.teamwiney.auth.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.teamwiney.auth.login.LoginContract
import com.teamwiney.auth.login.LoginViewModel
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.navigation.AuthDestinations
import com.teamwiney.core.design.R
import com.teamwiney.ui.splash.SplashBackground
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SplashScreen(
    appState: WineyAppState,
) {
    val viewModel: SplashViewModel = hiltViewModel()

    LaunchedEffect(true) {
        delay(1000)
        viewModel.processEvent(SplashContract.Event.AutoLoginCheck)
        viewModel.effect.collectLatest {
            when (it) {
                is SplashContract.Effect.NavigateTo -> {
                    appState.navigate(it.destination, builder = it.builder)
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
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
}