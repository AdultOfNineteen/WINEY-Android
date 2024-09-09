package com.teamwiney.winey

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.navOptions
import com.teamwiney.core.common.NetworkMonitor
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.navigation.AuthDestinations
import com.teamwiney.core.common.rememberWineyAppState
import com.teamwiney.core.common.rememberWineyBottomSheetState
import com.teamwiney.ui.theme.WineyTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var tokenExpiredReceiver: BroadcastReceiver? = null
    private var isReceiverRegistered = false

    private lateinit var appState: WineyAppState

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // WindowInset 직접 조절하기 위해서
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // 리시버 등록
        registerTokenExpiredReceiver()

        setContent {
            WineyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = WineyTheme.colors.background_1
                ) {
                    appState = rememberWineyAppState(
                        networkMonitor = networkMonitor
                    )
                    WineyNavHost(
                        appState = appState,
                        bottomSheetState = rememberWineyBottomSheetState()
                    )
                }
            }
        }
    }

    private fun registerTokenExpiredReceiver() {
        if (!isReceiverRegistered) {
            val intentFilter = IntentFilter("com.teamwiney.winey.TOKEN_EXPIRED")
            tokenExpiredReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
                    // Handle token expired
                    appState.showSnackbar("로그인 후 이용해주세요.")
                    appState.navController.navigate(
                        AuthDestinations.Login.LOGIN,
                        navOptions {
                            popUpTo(AuthDestinations.Login.LOGIN) {
                                inclusive = true
                            }
                        }
                    )
                }
            }
            LocalBroadcastManager.getInstance(this).registerReceiver(tokenExpiredReceiver!!, intentFilter)
            isReceiverRegistered = true
        }
    }

    private fun unregisterTokenExpiredReceiver() {
        if (isReceiverRegistered && tokenExpiredReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(tokenExpiredReceiver!!)
            isReceiverRegistered = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // 리시버 해제
        unregisterTokenExpiredReceiver()
    }
}
