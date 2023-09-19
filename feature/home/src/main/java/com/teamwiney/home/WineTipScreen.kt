package com.teamwiney.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.rememberWineyAppState
import com.teamwiney.ui.components.TipCard
import com.teamwiney.ui.components.TopBar
import com.teamwiney.ui.theme.WineyTheme

@Preview
@Composable
fun WineTipScreen(
    appState: WineyAppState = rememberWineyAppState()
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
        TipContent()
    }
}

@Composable
fun TipContent() {

    val tipItems = List(12) {
        "와인이 처음이여서 뭘 마셔야할지 모르겠다면?"
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(tipItems) {
            TipCard(
                modifier = Modifier.fillMaxWidth(),
                title = it
            )
        }
    }
}