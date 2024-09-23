package com.teamwiney.notewrite

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.navigation.NoteDestinations
import com.teamwiney.data.network.model.response.SearchWine
import com.teamwiney.data.network.model.response.Wine
import com.teamwiney.notecollection.components.NoteWineCard
import com.teamwiney.notewrite.components.WineSearchTextField
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.HeightSpacerWithLine
import com.teamwiney.ui.theme.WineyTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun NoteWineSearchScreen(
    appState: WineyAppState,
    viewModel: NoteWriteViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val effectFlow = viewModel.effect

    val searchWines = uiState.searchWines.collectAsLazyPagingItems()
    val searchWinesRefreshState = searchWines.loadState.refresh
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(searchWinesRefreshState) {
        if (searchWinesRefreshState is LoadState.Error) {
            val errorMessage = searchWinesRefreshState.error.message ?: "네트워크 오류가 발생했습니다."
            appState.showSnackbar(errorMessage)
        }
    }

    LaunchedEffect(true) {
        viewModel.loadTastingNote()

        effectFlow.collectLatest { effect ->
            when (effect) {
                is NoteWriteContract.Effect.NavigateTo -> {
                    appState.navigate(effect.destination, effect.navOptions)
                }

                is NoteWriteContract.Effect.ShowSnackBar -> {
                    appState.showSnackbar(effect.message)
                }

                else -> {}
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WineyTheme.colors.background_1)
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
    ) {
        WineSearchTopBar(
            appState = appState,
            searchKeyword = uiState.searchKeyword,
            onValueChange = viewModel::updateSearchKeyword,
            onSearch = {
                viewModel.getSearchWines()
                keyboardController?.hide()
            }
        )

        HeightSpacer(height = 8.dp)

        Text(
            modifier = Modifier.padding(horizontal = 24.dp),
            text = buildAnnotatedString {
                append("검색 결과 ")
                withStyle(style = SpanStyle(WineyTheme.colors.main_3)) {
                    append("${uiState.searchWinesCount}개")
                }
            },
            color = WineyTheme.colors.gray_50,
            style = WineyTheme.typography.headline
        )

        HeightSpacerWithLine(
            modifier = Modifier.padding(top = 20.dp),
            color = WineyTheme.colors.gray_900
        )

        if (uiState.searchWinesCount == 0) EmptySearch()

        LazyColumn {
            items(
                count = searchWines.itemCount,
                key = searchWines.itemKey(),
                contentType = searchWines.itemContentType()
            ) { index ->
                searchWines[index]?.let {
                    NoteSearchItem(
                        wine = it,
                        searchKeyword = uiState.searchKeyword
                    )
                }
            }
        }
    }

}

@Composable
fun WineSearchTopBar(
    appState: WineyAppState,
    searchKeyword: String,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit
) {
    val focusRequester by remember { mutableStateOf(FocusRequester()) }

    LaunchedEffect(key1 = Unit) {
        focusRequester.requestFocus()
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(5.dp))

        Icon(
            painter = painterResource(id = com.teamwiney.core.design.R.drawable.ic_back_arrow_48),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .clip(CircleShape)
                .clickable {
                    appState.navController.navigateUp()
                }
        )
        WineSearchTextField(
            modifier = Modifier
                .weight(1f)
                .focusRequester(focusRequester = focusRequester),
            value = searchKeyword,
            onValueChange = onValueChange,
            placeholderText = "기록할 와인을 검색해주세요!",
            onSearch = onSearch
        )

        Spacer(modifier = Modifier.width(24.dp))
    }
}

@Preview
@Composable
fun EmptySearch() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = com.teamwiney.core.design.R.mipmap.img_search_wine_empty),
                contentDescription = "IMG_SEARCH_WINE_EMPTY"
            )
            Text(
                text = "검색결과가 없어요!\n비슷한 와인명으로 입력해보세요",
                style = WineyTheme.typography.headline,
                color = WineyTheme.colors.gray_800,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun NoteSearchItem(
    wine: SearchWine,
    searchKeyword: String
) {
    val annotatedText = buildAnnotatedString {
        val name = wine.name
        val keyword = searchKeyword

        // 키워드가 이름에 포함되어 있을 경우
        val startIndex = name.indexOf(keyword, ignoreCase = true)
        if (startIndex >= 0) {
            // 키워드 앞의 텍스트
            append(name.substring(0, startIndex))

            // 키워드에 스타일 적용
            withStyle(style = SpanStyle(color = WineyTheme.colors.main_3)) {
                append(name.substring(startIndex, startIndex + keyword.length))
            }

            // 키워드 뒤의 텍스트
            append(name.substring(startIndex + keyword.length))
        } else {
            // 키워드가 없으면 전체 텍스트 그대로
            append(name)
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            modifier = Modifier.padding(20.dp),
            text = annotatedText,
            style = WineyTheme.typography.bodyM1.copy(
                color = WineyTheme.colors.gray_50
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(
                    WineyTheme.colors.gray_900
                )
        )
    }
}
