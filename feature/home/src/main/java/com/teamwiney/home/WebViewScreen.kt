package com.teamwiney.home

import android.util.Log
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.rememberWineyAppState
import com.teamwiney.ui.components.TopBar
import com.teamwiney.ui.theme.WineyTheme

@Preview
@Composable
fun WebViewScreen(
    appState: WineyAppState = rememberWineyAppState(),
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
            annotatedContent = buildAnnotatedString {
                append("와인 초보를 위한 ")
                withStyle(style = SpanStyle(WineyTheme.colors.main_2)) {
                    append("TIP")
                }
            },
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
    Log.d("url", url)

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
        }, update = {
            it.loadUrl(url)
        }
    )
}