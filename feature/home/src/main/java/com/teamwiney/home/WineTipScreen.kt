package com.teamwiney.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.teamwiney.analysis.component.TipCard
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.navigation.HomeDestinations
import com.teamwiney.data.network.model.response.WineTip
import com.teamwiney.ui.components.TopBar
import com.teamwiney.ui.theme.WineyTheme

@Composable
fun WineTipScreen(
    appState: WineyAppState,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val wineTips = uiState.wineTips.collectAsLazyPagingItems()
    val wineTipsRefreshState = wineTips.loadState.refresh

    LaunchedEffect(wineTipsRefreshState) {
        if (wineTipsRefreshState is LoadState.Error) {
            val errorMessage = wineTipsRefreshState.error.message ?: "네트워크 오류가 발생했습니다."
            appState.showSnackbar(errorMessage)
        }
    }

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
        TipContent(
            appState = appState,
            wineTips = wineTips
        )
    }
}

@Composable
fun TipContent(
    appState: WineyAppState,
    wineTips: LazyPagingItems<WineTip>
) {

    val title = "와인 초보자를 위한 "
    val subTitle = "TIP"
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(
            count = wineTips.itemCount,
            key = wineTips.itemKey(),
            contentType = wineTips.itemContentType()
        ) { index ->
            wineTips[index]?.let {
                TipCard(
                    title = it.title,
                    thumbnail = it.thumbnail,
                    onClick = {
                        appState.navigate("${HomeDestinations.WEB_VIEW}?url=${it.url}&title=${title}&subTitle=${subTitle}")
                    }
                )
            }
        }
    }
}