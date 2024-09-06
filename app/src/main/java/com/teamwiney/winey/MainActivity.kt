package com.teamwiney.winey

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.teamwiney.core.common.NetworkMonitor
import com.teamwiney.core.common.rememberWineyAppState
import com.teamwiney.core.common.rememberWineyBottomSheetState
import com.teamwiney.ui.theme.WineyTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent
        Log.d("DeepLink", "${intent.data}")

        // WindowInset 직접 조절하기 위해서
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            WineyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = WineyTheme.colors.background_1
                ) {
                    WineyNavHost(
                        appState = rememberWineyAppState(
                            networkMonitor = networkMonitor
                        ),
                        bottomSheetState = rememberWineyBottomSheetState()
                    )
                }
            }
        }
    }


}