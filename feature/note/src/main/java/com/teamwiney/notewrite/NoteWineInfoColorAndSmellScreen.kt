package com.teamwiney.notewrite

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.teamwiney.core.common.AmplitudeEvent
import com.teamwiney.core.common.AmplitudeProvider
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.WineyBottomSheetState
import com.teamwiney.core.common.navigation.NoteDestinations
import com.teamwiney.core.common.navigation.NoteDestinations.Write.INFO_STANDARD_SMELL
import com.teamwiney.notedetail.component.NoteFeatureText
import com.teamwiney.notewrite.components.WineSmellDirectInputBottomSheet
import com.teamwiney.ui.components.ColorSlider
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.TopBar
import com.teamwiney.ui.components.WButton
import com.teamwiney.ui.theme.WineyTheme

data class WineSmellKeyword(
    val title: String,
    val options: List<WineSmellOption>,
)

data class WineSmellOption(
    val name: String,
    val value: String
)


@Composable
fun NoteWineInfoColorAndSmellScreen(
    appState: WineyAppState,
    bottomSheetState: WineyBottomSheetState,
    viewModel: NoteWriteViewModel,
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BackHandler {
        appState.navController.navigateUp()
        AmplitudeProvider.trackEvent(AmplitudeEvent.COLOR_SCENT_INPUT_BACK_CLICK)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WineyTheme.colors.background_1)
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
    ) {
        TopBar(
            content = "와인 정보 입력",
        ) {
            appState.navController.navigateUp()
            AmplitudeProvider.trackEvent(AmplitudeEvent.COLOR_SCENT_INPUT_BACK_CLICK)
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
        ) {
            WineColorPicker(
                initialColor = uiState.initialColor,
                currentColor = uiState.writeTastingNote.color,
                barColors = uiState.barColors
            ) {
                viewModel.updateColor(it)
            }
            HeightSpacer(35.dp)
            WineFlavorPicker(
                bottomSheetState = bottomSheetState,
                wineSmellKeywords = uiState.wineSmellKeywords,
                wineDirectInputSmellKeywords = uiState.wineDirectInputSmellKeywords,
                addDirectInputSmellKeyword = viewModel::addDirectInputSmellKeyword,
                isWineSmellKeywordSelected = viewModel::isWineSmellSelected,
                updateWineSmell = { wineSmellOption ->
                    viewModel.updateWineSmell(wineSmellOption)
                },
                isWineDirectInputSmellKeywordSelected = viewModel::isWineDirectInputSmellSelected,
                updateDirectInputWineSmell = viewModel::updateDirectInputWineSmell,
                navigateToStandardSmell = {
                    appState.navigate(INFO_STANDARD_SMELL)
                    AmplitudeProvider.trackEvent(AmplitudeEvent.SCENT_HELP_CLICK)
                }
            )
        }

        Row(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(top = 20.dp, bottom = 40.dp),
        ) {
            WButton(
                text = "다음",
                modifier = Modifier
                    .weight(3f),
                enableBackgroundColor = WineyTheme.colors.main_2,
                disableBackgroundColor = WineyTheme.colors.gray_900,
                disableTextColor = WineyTheme.colors.gray_600,
                enableTextColor = WineyTheme.colors.gray_50,
                onClick = {
                    appState.navController.navigate(NoteDestinations.Write.INFO_FLAVOR)
                    AmplitudeProvider.trackEvent(AmplitudeEvent.COLOR_SCENT_INPUT_NEXT_CLICK)
                }
            )
        }
    }
}

@Composable
private fun WineFlavorPicker(
    bottomSheetState: WineyBottomSheetState,
    wineSmellKeywords: List<WineSmellKeyword>,
    addDirectInputSmellKeyword: (String) -> Unit,
    isWineSmellKeywordSelected: (WineSmellOption) -> Boolean,
    updateWineSmell: (WineSmellOption) -> Unit = {},
    wineDirectInputSmellKeywords: List<String>,
    isWineDirectInputSmellKeywordSelected: (String) -> Boolean,
    updateDirectInputWineSmell: (String) -> Unit = {},
    navigateToStandardSmell: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = WineyTheme.colors.gray_50
                        )
                    ) {
                        append("와인 향은요? ")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = WineyTheme.colors.gray_600,
                            fontSize = 14.sp
                        )
                    ) {
                        append("(선택)")
                    }
                },
                style = WineyTheme.typography.bodyB1
            )
            Text(
                text = "향표현이 어려워요!",
                style = WineyTheme.typography.captionM2,
                color = WineyTheme.colors.gray_500,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    navigateToStandardSmell()
                }
            )
        }
        HeightSpacer(20.dp)
        Column(
            verticalArrangement = Arrangement.spacedBy(25.dp)
        ) {
            wineSmellKeywords.forEach {
                WineSmellContainer(
                    wineSmellKeyword = it,
                    isWineSmellKeywordSelected = isWineSmellKeywordSelected,
                    updateWineSmell = updateWineSmell
                )
            }

            if (wineDirectInputSmellKeywords.isNotEmpty()) {
                WineDirectInputSmellContainer(
                    wineSmellKeywords = wineDirectInputSmellKeywords,
                    isWineSmellKeywordSelected = isWineDirectInputSmellKeywordSelected,
                    updateWineSmell = updateDirectInputWineSmell
                )
            }

            Box(
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                SmellDirectInputButton {
                    bottomSheetState.showBottomSheet {
                        WineSmellDirectInputBottomSheet(
                            onConfirm = {
                                addDirectInputSmellKeyword(it)
                                bottomSheetState.hideBottomSheet()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun WineSmellContainer(
    wineSmellKeyword: WineSmellKeyword,
    isWineSmellKeywordSelected: (WineSmellOption) -> Boolean,
    updateWineSmell: (WineSmellOption) -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            text = wineSmellKeyword.title,
            modifier = Modifier.padding(start = 24.dp),
            style = WineyTheme.typography.bodyB2,
            color = WineyTheme.colors.gray_500
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(wineSmellKeyword.options) {
                NoteFeatureText(
                    name = it.name,
                    enabled = isWineSmellKeywordSelected(it),
                ) {
                    updateWineSmell(it)
                }
            }
        }
    }
}

@Composable
private fun WineDirectInputSmellContainer(
    wineSmellKeywords: List<String>,
    isWineSmellKeywordSelected: (String) -> Boolean,
    updateWineSmell: (String) -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            text = "직접 추가",
            modifier = Modifier.padding(start = 24.dp),
            style = WineyTheme.typography.bodyB2,
            color = WineyTheme.colors.gray_500
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(wineSmellKeywords) {
                NoteFeatureText(
                    name = it,
                    enabled = isWineSmellKeywordSelected(it),
                ) {
                    updateWineSmell(it)
                }
            }
        }
    }
}

@Composable
private fun WineColorPicker(
    initialColor: Color,
    currentColor: Color,
    barColors: List<Color>,
    updateCurrentColor: (Color) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, top = 20.dp)
    ) {
        Text(
            text = "와인 컬러는요?",
            style = WineyTheme.typography.bodyB1,
            color = WineyTheme.colors.gray_50,
        )
        HeightSpacer(height = 10.dp)
        Text(
            text = "드신 와인 색감에 맞게 핀을 설정해주세요!",
            style = WineyTheme.typography.bodyB2,
            color = WineyTheme.colors.gray_800,
        )
        HeightSpacer(height = 30.dp)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(WineyTheme.colors.background_1),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Spacer(
                        modifier = Modifier
                            .background(
                                brush = Brush.radialGradient(
                                    listOf(
                                        currentColor,
                                        Color.Transparent
                                    )
                                ),
                                shape = CircleShape
                            )
                            .size(48.dp)
                    )

                    ColorSlider(
                        initialColor = initialColor,
                        onValueChange = updateCurrentColor,
                        barColors = barColors,
                        trackHeight = 10.dp,
                        thumbSize = 22.dp
                    )
                }
            }
        }
    }
}

@Composable
private fun SmellDirectInputButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        border = BorderStroke(
            width = 1.dp,
            color = WineyTheme.colors.main_2
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
        ),
        contentPadding = PaddingValues(vertical = 14.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(
            text = "향 추가하기",
            color = WineyTheme.colors.main_2,
            style = WineyTheme.typography.bodyM2.copy(
                color = WineyTheme.colors.main_2
            ),
        )
    }
}