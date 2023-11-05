package com.teamwiney.notewrite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.WineyBottomSheetState
import com.teamwiney.core.common.rememberWineyAppState
import com.teamwiney.core.common.rememberWineyBottomSheetState
import com.teamwiney.notecollection.components.NoteWineCard
import com.teamwiney.ui.components.TopBar
import com.teamwiney.ui.components.WButton
import com.teamwiney.ui.theme.WineyTheme

@Composable
@Preview
fun NoteWriteSelectWineScreen(
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
            .navigationBarsPadding()
            .statusBarsPadding()
    ) {
        TopBar(
            content = ""
        ) {
            appState.navController.navigateUp()
        }

        Text(
            text = "해당 와인으로\n노트를 작성할까요?",
            style = WineyTheme.typography.title2.copy(color = WineyTheme.colors.gray_50),
            modifier = Modifier
                .padding(horizontal = 110.dp)
                .padding(top = 30.dp, bottom = 60.dp),
            textAlign = TextAlign.Center
        )

        NoteWineCard(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(0.5f)
                .aspectRatio(1f),
            color = "RED",
            name = "와인 이름",
            origin = "와인 원산지",
            starRating = 3,
            onClick = {
                // TODO 노트 작성 화면으로
            },
        )

        Text(
            modifier = Modifier
                .padding(top = 40.dp)
                .align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            text = "캄포 마리나 프리미티도 디 만두리아",
            style = WineyTheme.typography.bodyM1.copy(color = WineyTheme.colors.gray_50)
        )
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            text = "이탈리아 / 프리미티보",
            style = WineyTheme.typography.captionB1.copy(color = WineyTheme.colors.gray_700)
        )

        Spacer(modifier = Modifier.weight(1f))
        WButton(
            modifier = Modifier
                .padding(bottom = 100.dp)
                .padding(horizontal = 76.dp)
                .align(Alignment.CenterHorizontally),
            text = "노트 작성하기",
            enableTextStyle = WineyTheme.typography.bodyB2.copy(color = WineyTheme.colors.gray_50),
            disableTextStyle = WineyTheme.typography.bodyB2.copy(color = WineyTheme.colors.gray_50),
            shape = RoundedCornerShape(46.dp),
            onClick = {
                // TODO 노트 작성 화면으로
            }
        )
    }
}