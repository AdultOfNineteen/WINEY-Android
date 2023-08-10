package com.teamwiney.winey

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.teamwiney.ui.theme.WineyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // WindowInset 직접 조절하기 위해서
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            WineyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = WineyTheme.colors.background_1
                ) {
                    WineyNavHost()
                }
            }
        }
    }
}