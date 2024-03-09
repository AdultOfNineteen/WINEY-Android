package com.teamwiney.notewrite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.WineyBottomSheetState
import com.teamwiney.core.common.navigation.NoteDestinations
import com.teamwiney.ui.components.HintPopUp
import com.teamwiney.ui.components.TopBar
import com.teamwiney.ui.components.WButton
import com.teamwiney.ui.components.bottomBorder
import com.teamwiney.ui.theme.LocalColors
import com.teamwiney.ui.theme.WineyTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NoteWineInfoVintageAndPriceScreen(
    appState: WineyAppState,
    bottomSheetState: WineyBottomSheetState,
    viewModel: NoteWriteViewModel
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val focusRequester by remember { mutableStateOf(FocusRequester()) }
    val focusRequester2 by remember { mutableStateOf(FocusRequester()) }

    LaunchedEffect(key1 = Unit) {
        focusRequester.requestFocus()
        viewModel.showHintPopup()
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
            viewModel.hideHintPopup()
            appState.navController.navigateUp()
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "빈티지를 입력해주세요!",
                style = WineyTheme.typography.bodyB1,
                color = WineyTheme.colors.gray_50,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )


            WineInfoTextField(
                value = uiState.wineNote.vintage,
                onValueChanged = { viewModel.updateVintage(it) },
                placeholderText = "ex) 1990",
                trailingIcon = {
                    Text(
                        text = "년도",
                        style = WineyTheme.typography.bodyB2,
                        color = WineyTheme.colors.gray_900
                    )
                },
                modifier = Modifier
                    .padding(horizontal = 90.dp)
                    .focusRequester(focusRequester = focusRequester),
                placeholderTextAlign = TextAlign.Center,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusRequester2.requestFocus()
                }),
                maxLength = 4
            )

            Text(
                text = "구매시 가격을 입력해주세요!",
                style = WineyTheme.typography.bodyB1,
                color = WineyTheme.colors.gray_50,
                modifier = Modifier
                    .padding(top = 70.dp)
                    .align(Alignment.CenterHorizontally)
            )

            WineInfoTextField(
                value = uiState.wineNote.price,
                onValueChanged = { viewModel.updatePrice(it) },
                placeholderText = "ex) 30000",
                trailingIcon = {
                    Text(
                        text = "원",
                        style = WineyTheme.typography.bodyB2,
                        color = WineyTheme.colors.gray_900
                    )
                },
                modifier = Modifier
                    .padding(horizontal = 90.dp)
                    .focusRequester(focusRequester2),
                placeholderTextAlign = TextAlign.Center,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                keyboardActions = KeyboardActions(onDone = {
                    if (uiState.wineNote.price.isNotEmpty() && uiState.wineNote.vintage.isNotEmpty()) {
                        appState.navController.navigate(NoteDestinations.Write.INFO_COLOR_SMELL)
                    }
                }),
                maxLength = 7
            )
        }

        Row(
            modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 40.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Box(
                modifier = Modifier.weight(2f)
            ) {
                var buttonHeight by remember { mutableIntStateOf(0) }
                val density = LocalDensity.current

                WButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { coordinates ->
                            buttonHeight =
                                density.run { coordinates.size.height + 15.dp.roundToPx() }
                        },
                    text = "건너뛰기",
                    enableBackgroundColor = WineyTheme.colors.gray_950,
                    onClick = {
                        appState.navController.navigate(NoteDestinations.Write.INFO_COLOR_SMELL)
                    }
                )
                if (uiState.hintPopupOpen) {
                    HintPopUp(
                        isReversed = true,
                        backgroundColor = WineyTheme.colors.gray_900,
                        text = "건너뛰기를 누르면 내용이 저장되지 않아요",
                        offset = IntOffset(density.run { 30.dp.roundToPx() }, -buttonHeight)
                    )
                }
            }

            WButton(
                text = "다음",
                modifier = Modifier.weight(3f),
                enableBackgroundColor = WineyTheme.colors.main_2,
                disableBackgroundColor = WineyTheme.colors.gray_900,
                disableTextColor = WineyTheme.colors.gray_600,
                enableTextColor = WineyTheme.colors.gray_50,
                enabled = uiState.wineNote.price.isNotEmpty() || uiState.wineNote.vintage.isNotEmpty(),
                onClick = {
                    appState.navController.navigate(NoteDestinations.Write.INFO_COLOR_SMELL)
                }
            )
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