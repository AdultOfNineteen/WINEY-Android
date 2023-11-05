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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.WineyBottomSheetState
import com.teamwiney.core.common.rememberWineyAppState
import com.teamwiney.core.common.rememberWineyBottomSheetState
import com.teamwiney.notecollection.components.NoteWineCard
import com.teamwiney.notewrite.components.WineSearchTextField
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.HeightSpacerWithLine
import com.teamwiney.ui.theme.WineyTheme

@Composable
@Preview
fun NoteWineSearchScreen(
    appState: WineyAppState = rememberWineyAppState(),
    wineyBottomSheetState: WineyBottomSheetState = rememberWineyBottomSheetState()
) {
    var text by remember {
        mutableStateOf("")
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
            text = text,
            onValueChange = { text = it }
        )
        
        HeightSpacer(height = 8.dp)
        
        Text(
            modifier = Modifier.padding(horizontal = 24.dp),
            text = buildAnnotatedString {
                append("검색 결과 ")
                withStyle(style = SpanStyle(WineyTheme.colors.main_3)) {
                    append("${1}개")
                }
            },
            color = WineyTheme.colors.gray_50,
            style = WineyTheme.typography.headline
        )
        
        HeightSpacerWithLine(
            modifier = Modifier.padding(top = 20.dp),
            color = WineyTheme.colors.gray_900
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 26.dp),
            verticalArrangement = Arrangement.spacedBy(21.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            item {
                NoteWineCard(
                    color = "RED", name = "와인", origin = "미국", starRating = 5) {
                }
            }

            item {
                NoteWineCard(
                    color = "RED", name = "와인", origin = "미국", starRating = 5) {
                }
            }

            item {
                NoteWineCard(
                    color = "RED", name = "와인", origin = "미국", starRating = 5) {
                }
            }

            item {
                NoteWineCard(
                    color = "RED", name = "와인", origin = "미국", starRating = 5) {
                }
            }

            item {
                NoteWineCard(
                    color = "RED", name = "와인", origin = "미국", starRating = 5) {
                }
            }
        }
    }
}

@Composable
fun WineSearchTopBar(
    appState: WineyAppState,
    text: String,
    onValueChange: (String) -> Unit
) {
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
                .clickable { appState.navController.navigateUp() }
        )
        WineSearchTextField(
            modifier = Modifier.weight(1f),
            value = text,
            onValueChange = onValueChange,
            placeholderText = "기록할 와인을 검색해주세요!"
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