package com.teamwiney.notewrite

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.navigation.NoteDestinations
import com.teamwiney.core.common.navigation.NoteDestinations.Write.INFO_STANDARD_SMELL
import com.teamwiney.core.common.rememberWineyAppState
import com.teamwiney.notedetail.component.NoteFeatureText
import com.teamwiney.ui.components.ColorSlider
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.TopBar
import com.teamwiney.ui.components.WButton
import com.teamwiney.ui.components.bottomBorder
import com.teamwiney.ui.theme.LocalColors
import com.teamwiney.ui.theme.Pretendard
import com.teamwiney.ui.theme.WineyTheme

data class WineSmell(
    val title: String,
    val options: List<WineSmellOption>,
)

data class WineSmellOption(
    val name: String,
    var isSelected: Boolean = false
)


@Preview
@Composable
fun NoteWineInfoColorAndSmellScreen(
    appState: WineyAppState = rememberWineyAppState(),
    viewModel: NoteWriteViewModel = hiltViewModel(),
) {

    val wineSmells = remember {
        mutableStateListOf<WineSmell>(
            WineSmell(
                title = "과일향",
                options = listOf(
                    WineSmellOption("과일향"),
                    WineSmellOption("베리류"),
                    WineSmellOption("레몬/라임"),
                    WineSmellOption("사과/배"),
                    WineSmellOption("복숭아/자두")
                )
            ),
            WineSmell(
                title = "내추럴",
                options = listOf(
                    WineSmellOption("꽃향"),
                    WineSmellOption("풀/나무"),
                    WineSmellOption("허브향")
                )
            ),
            WineSmell(
                title = "오크향",
                options = listOf(
                    WineSmellOption("오크향"),
                    WineSmellOption("향신로"),
                    WineSmellOption("견과류"),
                    WineSmellOption("바닐라"),
                    WineSmellOption("초콜릿")
                )
            ),
            WineSmell(
                title = "기타",
                options = listOf(
                    WineSmellOption("부싯돌"),
                    WineSmellOption("빵"),
                    WineSmellOption("고무"),
                    WineSmellOption("흙/재"),
                    WineSmellOption("약품")
                )
            ),
        )
    }
    val startColor = Color.Red
    val endColor = Color.White
    var currentColor by remember { mutableStateOf(startColor) }

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
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
        ) {
            WineColorPicker(
                currentColor = currentColor,
                startColor = startColor,
                endColor = endColor
            ) { currentColor = it }
            HeightSpacer(35.dp)
            WineFavorPicker(
                wineSmells = wineSmells,
                updateWineSmell = { wineSmellOption ->
                    wineSmells.find { it.options.any { it.name == wineSmellOption.name } }
                        ?.let { wineSmell ->
                            val index = wineSmells.indexOfFirst { it.title == wineSmell.title }
                            wineSmells[index] = wineSmell.copy(
                                options = wineSmell.options.map {
                                    if (it.name == wineSmellOption.name) {
                                        wineSmellOption
                                    } else {
                                        it
                                    }
                                }
                            )
                        }
                },
                navigateToStandardSmell = {
                    appState.navigate(INFO_STANDARD_SMELL)
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
                    // TODO 컬러 파싱 어떻게?
                    viewModel.updateColor(currentColor.toString())
                    viewModel.updateSmellKeywordList(
                        wineSmells.map { it.options }.flatten().filter { it.isSelected })
                    appState.navController.navigate(NoteDestinations.Write.INFO_FLAVOR)
                }
            )
        }
    }
}

@Composable
private fun WineFavorPicker(
    wineSmells: List<WineSmell>,
    updateWineSmell: (WineSmellOption) -> Unit = {}, navigateToStandardSmell: () -> Unit
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
                            fontFamily = Pretendard,
                            color = WineyTheme.colors.gray_50,
                            fontSize = 17.sp
                        )
                    ) {
                        append("와인 향은요? ")
                    }
                    withStyle(
                        style = SpanStyle(
                            fontFamily = Pretendard,
                            color = WineyTheme.colors.gray_600,
                            fontSize = 14.sp
                        )
                    ) {
                        append("(선택)")
                    }
                }
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
            wineSmells.forEach {
                WineSmellContainer(wineSmell = it, updateWineSmell = updateWineSmell)
            }
        }
    }
}

@Composable
private fun WineSmellContainer(
    wineSmell: WineSmell,
    updateWineSmell: (WineSmellOption) -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            text = wineSmell.title,
            modifier = Modifier.padding(start = 24.dp),
            style = WineyTheme.typography.bodyB2,
            color = WineyTheme.colors.gray_500
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Spacer(modifier = Modifier.width(14.dp))
            wineSmell.options.forEach {
                NoteFeatureText(
                    name = it.name,
                    enable = it.isSelected,
                ) {
                    updateWineSmell(it.copy(isSelected = !it.isSelected))
                }
            }
            Spacer(modifier = Modifier.width(14.dp))
        }
    }
}

@Composable
private fun WineColorPicker(
    currentColor: Color,
    startColor: Color,
    endColor: Color,
    updateCurrentColor: (Color) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 20.dp)
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
                        onValueChange = updateCurrentColor,
                        startColor = startColor,
                        endColor = endColor,
                        trackHeight = 10.dp,
                        thumbSize = 22.dp
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun WineInfoTextField(
    value: String = "asdasd",
    onValueChanged: (String) -> Unit = {},
    modifier: Modifier = Modifier,
    trailingIcon: @Composable (() -> Unit)? = null,
    placeholderText: String = "",
    fontSize: TextUnit = 16.sp,
    focusRequest: FocusRequester? = null,
    keyboardOptions: KeyboardOptions? = null,
    keyboardActions: KeyboardActions? = null,
    onFocusedChange: (Boolean) -> Unit = {},
    onErrorState: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    maxLength: Int = 25,
    placeholderTextAlign: TextAlign = TextAlign.Center,
) {
    val localColors = LocalColors.current
    val bottomLineColor = remember {
        mutableStateOf(localColors.gray_800)
    }

    Column(modifier = modifier) {
        BasicTextField(
            modifier = Modifier
                .onFocusChanged {
                    onFocusedChange(it.isFocused)
                    if (it.isFocused) {
                        bottomLineColor.value = localColors.gray_50
                    } else {
                        bottomLineColor.value = localColors.gray_800
                    }
                }
                .bottomBorder(1.dp, if (onErrorState) localColors.error else bottomLineColor.value)
                .focusRequester(focusRequest ?: FocusRequester()),
            value = value,
            onValueChange = {
                if (it.length <= maxLength) onValueChanged(it)
            },
            singleLine = true,
            cursorBrush = SolidColor(Color.White),
            textStyle = WineyTheme.typography.bodyM1.copy(
                color = localColors.gray_50,
                textAlign = TextAlign.Center
            ),
            keyboardOptions = keyboardOptions ?: KeyboardOptions(),
            keyboardActions = keyboardActions ?: KeyboardActions(),
            decorationBox = { innerTextField ->
                Box(
                    Modifier
                        .padding(0.dp, 9.dp)
                        .fillMaxWidth()
                ) {
                    if (value.isEmpty()) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = placeholderText,
                            style = LocalTextStyle.current.copy(
                                color = localColors.gray_800,
                                fontSize = fontSize,
                            ),
                            textAlign = placeholderTextAlign,
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        innerTextField()
                    }
                    if (trailingIcon != null) Box(modifier = Modifier.align(Alignment.CenterEnd)) {
                        trailingIcon()
                    }
                }
            },
            visualTransformation = visualTransformation,
        )
    }
}