package com.teamwiney.notewrite

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.rememberWineyAppState
import com.teamwiney.core.design.R
import com.teamwiney.notedetail.component.NoteFeatureText
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.TopBar
import com.teamwiney.ui.theme.WineyTheme


@Preview
@Composable
fun NoteWineInfoStandardSmellScreen(
    appState: WineyAppState = rememberWineyAppState(),
    viewModel: NoteWriteViewModel = hiltViewModel(),
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WineyTheme.colors.background_1)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        TopBar {
            appState.navController.navigateUp()
        }
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = buildAnnotatedString {
                    append("와인 품종에 따른\n")
                    withStyle(style = SpanStyle(WineyTheme.colors.main_2)) {
                        append("대표적인 향을")
                    }
                    append("느껴보세요")
                },
                color = WineyTheme.colors.gray_50,
                style = WineyTheme.typography.title2
            )
            HeightSpacer(height = 30.dp)

            Column(verticalArrangement = Arrangement.spacedBy(50.dp)) {
                repeat(3) {
                    Column {
                        Text(
                            text = "레드와인",
                            style = WineyTheme.typography.headline,
                            color = WineyTheme.colors.gray_500,
                            modifier = Modifier.padding(bottom = 20.dp)
                        )
                        Column(
                            verticalArrangement = Arrangement.spacedBy(25.dp)
                        ) {
                            repeat(3) {
                                WineStandarFlavor()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun WineStandarFlavor() {

    var tagVisible by remember { mutableStateOf(false) }
    Column {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                tagVisible = !tagVisible
            }
        ) {
            Text(
                text = "사르도네",
                style = WineyTheme.typography.headline,
                color = if (tagVisible) WineyTheme.colors.gray_300 else WineyTheme.colors.gray_500
            )
            Icon(
                painter = painterResource(
                    id = if (tagVisible) R.drawable.ic_dropdown_down_15 else R.drawable.ic_dropdown_up_15
                ),
                tint = if (tagVisible) WineyTheme.colors.gray_600 else WineyTheme.colors.gray_300,
                contentDescription = "dropdown",
            )
        }
        AnimatedVisibility(
            visible = tagVisible,
            enter = slideInVertically { -it } + fadeIn(),
            exit = slideOutVertically { -it } + fadeOut(),
        ) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier.padding(top = 12.dp)
            ) {
                items(5) {
                    NoteFeatureText(
                        name = "과일향",
                    )
                }
            }
        }
    }
}
