package com.teamwiney.mypage

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.ui.components.TopBar
import com.teamwiney.ui.theme.WineyTheme

@Composable
fun MyPageTermsScreen(
    appState: WineyAppState,
    title: String = "",
    url: String = ""
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .statusBarsPadding()
            .background(WineyTheme.colors.background_1)
    ) {
        TopBar(
            content = title,
            leadingIconOnClick = {
                appState.navController.navigateUp()
            }
        )
        WebViewContent(url = url)
    }
}

@Composable
fun WebViewContent(
    url: String
) {
    AndroidView(
        factory = {
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = WebViewClient()
                loadUrl(url)
            }
        },
        update = {
            it.loadUrl(url)
        }
    )
}