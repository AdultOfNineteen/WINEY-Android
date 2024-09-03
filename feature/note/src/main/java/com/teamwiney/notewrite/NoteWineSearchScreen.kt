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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
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
    val scope = rememberCoroutineScope()
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

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 26.dp),
            verticalArrangement = Arrangement.spacedBy(21.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            items(
                count = searchWines.itemCount,
                key = searchWines.itemKey(),
                contentType = searchWines.itemContentType()
            ) { index ->
                searchWines[index]?.let {
                    NoteWineCard(
                        color = it.type.type,
                        name = it.name,
                        origin = it.country,
                        onClick = {
                            viewModel.updateSelectedWine(it)
                            appState.navigate(NoteDestinations.Write.SELECT_WINE)
                        }
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