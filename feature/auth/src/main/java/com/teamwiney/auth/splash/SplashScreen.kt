package com.teamwiney.auth.splash

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.teamwiney.auth.login.component.SplashBackground
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.design.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SplashScreen(
    appState: WineyAppState
) {
    val viewModel: SplashViewModel = hiltViewModel()
    val effectFlow = viewModel.effect

    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            appState.showSnackbar("추후에 설정창으로 이동해 알람을 허용할 수 있습니다.")
        }
    }

    LaunchedEffect(true) {
        viewModel.checkIsFirstLaunch()

        viewModel.getConnections()
        viewModel.registerFcmToken()
        delay(1500)
        viewModel.processEvent(SplashContract.Event.AutoLoginCheck)

        effectFlow.collectLatest { effect ->
            when (effect) {
                is SplashContract.Effect.ShowSnackBar -> {
                    appState.showSnackbar(effect.message)
                }

                is SplashContract.Effect.NavigateTo -> {
                    appState.navigate(effect.destination, builder = effect.builder)
                }

                is SplashContract.Effect.CheckPermission -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        val isGranted = ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.POST_NOTIFICATIONS
                        ) == PackageManager.PERMISSION_GRANTED

                        if (!isGranted) {
                            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        }
                    }
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