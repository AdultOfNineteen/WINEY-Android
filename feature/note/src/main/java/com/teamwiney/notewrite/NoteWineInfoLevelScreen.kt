package com.teamwiney.notewrite

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.navigation.NoteDestinations
import com.teamwiney.core.common.rememberWineyAppState
import com.teamwiney.core.design.R
import com.teamwiney.notewrite.components.NoteBackgroundSurface
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.HeightSpacerWithLine
import com.teamwiney.ui.components.NumberPicker
import com.teamwiney.ui.components.TopBar
import com.teamwiney.ui.components.WButton
import com.teamwiney.ui.theme.WineyTheme

@Preview
@Composable
fun NoteWineInfoLevelScreen(
    appState: WineyAppState = rememberWineyAppState(),
    viewModel: NoteWriteViewModel = hiltViewModel(),
) {

    val systemUiController = rememberSystemUiController()

    DisposableEffect(key1 = Unit) {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent
        )
        onDispose {
            systemUiController.setSystemBarsColor(
                color = Color(0xFF1F2126)
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WineyTheme.colors.background_1)
            .navigationBarsPadding()
            .imePadding()
    ) {
        Box(
            modifier = Modifier.weight(2.5f)
        ) {
            NoteBackgroundSurface(modifier = Modifier.fillMaxSize())
            Column(modifier = Modifier.statusBarsPadding()) {
                TopBar(
                    content = "와인 정보 입력",
                ) {
                    appState.navController.navigateUp()
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.mipmap.img_notewrite_badge),
                        contentDescription = null,
                        modifier = Modifier
                            .size(250.dp, 120.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = "와인의 기본 정보를 알려주세요!",
                        style = WineyTheme.typography.bodyB1,
                        color = WineyTheme.colors.gray_400,
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = "다음 구매시 참고를 돕고 추천드릴게요!",
                        style = WineyTheme.typography.captionM1,
                        color = WineyTheme.colors.gray_700,
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }

            }
        }
        Column(
            modifier = Modifier.weight(3f)
        ) {
            HeightSpacerWithLine(color = WineyTheme.colors.gray_900, height = 4.dp)
            HeightSpacer(height = 40.dp)
            Text(
                text = "도수를 입력해주세요.",
                style = WineyTheme.typography.bodyB1,
                color = WineyTheme.colors.gray_50,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            // TODO 넘버피커
            var value by remember { mutableIntStateOf(20) }
            NumberPicker(
                value = 12,
                onValueChange = {
                    value = it
                },
                range = 0..40,
                textStyle = WineyTheme.typography.title1.copy(
                    color = WineyTheme.colors.gray_50
                ),
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterHorizontally)
            )
            Row(
                modifier = Modifier.padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                WButton(
                    modifier = Modifier
                        .weight(2f),
                    text = "건너뛰기",
                    enableBackgroundColor = WineyTheme.colors.gray_950,
                )

                WButton(
                    text = "다음",
                    modifier = Modifier
                        .weight(3f)
                        .padding(bottom = 40.dp),
                    enableBackgroundColor = WineyTheme.colors.main_2,
                    disableBackgroundColor = WineyTheme.colors.gray_900,
                    disableTextColor = WineyTheme.colors.gray_600,
                    enableTextColor = WineyTheme.colors.gray_50,
                    enabled = true,
                    onClick = {
                        appState.navController.navigate(NoteDestinations.Write.INFO_VINTAGE_AND_PRICE)
                    }
                )
            }
        }

    }

}