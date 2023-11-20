@file:OptIn(ExperimentalGlideComposeApi::class)

package com.teamwiney.notewrite

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.rememberWineyAppState
import com.teamwiney.core.design.R
import com.teamwiney.notedetail.component.NoteFeatureText
import com.teamwiney.ui.components.TopBar
import com.teamwiney.ui.components.WButton
import com.teamwiney.ui.theme.Pretendard
import com.teamwiney.ui.theme.WineyTheme

@Composable
fun NoteWineInfoMemoScreen(
    appState: WineyAppState = rememberWineyAppState(),
    viewModel: NoteWriteViewModel = hiltViewModel(),
) {

    var wineMemo by remember { mutableStateOf("") }
    val selectedImage = remember { mutableStateListOf<Uri?>() }
    var repurchase by remember { mutableStateOf<Boolean?>(null) }
    var score by remember { mutableIntStateOf(0) }
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uris ->
            selectedImage.apply {
                clear()
                addAll(uris)
            }
        }
    )

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
                .fillMaxSize()
                .padding(20.dp)
                .weight(1f)
                .verticalScroll(rememberScrollState()),
        ) {
            Text(
                text = "마지막이에요!",
                style = WineyTheme.typography.bodyB1,
                color = WineyTheme.colors.main_2,
            )
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = WineyTheme.colors.gray_50,
                            fontSize = 17.sp
                        )
                    ) {
                        append("와인에 대한 메모를 작성해주세요. ")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = WineyTheme.colors.gray_600,
                            fontSize = 14.sp
                        )
                    ) {
                        append("(선택)")
                    }
                }
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .padding(vertical = 15.dp)
                    .horizontalScroll(rememberScrollState())
            ) {
                selectedImage.map {
                    Box {
                        GlideImage(
                            model = it,
                            contentDescription = "IMG_URL",
                            modifier = Modifier
                                .size(100.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(WineyTheme.colors.gray_900),
                            contentScale = ContentScale.Crop
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.ic_close_fill_18),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(7.dp)
                                .align(Alignment.TopEnd)
                                .clickable {
                                    selectedImage.remove(it)
                                }
                                .size(18.dp),
                            tint = Color.Unspecified
                        )
                    }
                }
            }

            Button(
                onClick = {
                    imagePicker.launch("image/*")
                },
                modifier = Modifier
                    .fillMaxWidth(),
                border = BorderStroke(1.dp, WineyTheme.colors.main_2),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                ),
                shape = RoundedCornerShape(5.dp),
                enabled = true
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "사진 첨부하기",
                        color = WineyTheme.colors.main_2,
                        style = WineyTheme.typography.bodyM2,
                        modifier = Modifier.padding(vertical = 8.5.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_camera_baseline_30),
                        contentDescription = null,
                        modifier = Modifier
                            .size(30.dp)
                    )
                }
            }


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
                    .heightIn(min = 150.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(
                        BorderStroke(
                            1.dp, WineyTheme.colors.main_3
                        ),
                        RoundedCornerShape(10.dp)
                    )
            ) {
                BasicTextField(
                    value = wineMemo,
                    onValueChange = { wineMemo = it },
                    modifier = Modifier
                        .padding(14.dp)
                        .fillMaxWidth()
                        .heightIn(min = 150.dp),
                    textStyle = WineyTheme.typography.captionM1.copy(
                        color = WineyTheme.colors.gray_50
                    ),
                    decorationBox = { innerTextField ->
                        Box {
                            if (wineMemo.isEmpty()) {
                                androidx.compose.material3.Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = "와인에 대한 생각을 작성해주세요 :)",
                                    style = LocalTextStyle.current.copy(
                                        color = WineyTheme.colors.gray_800,
                                        fontSize = 13.sp,
                                        fontFamily = Pretendard
                                    ),
                                    textAlign = TextAlign.Start,
                                )
                            }
                            innerTextField()
                        }
                    },
                )
            }
            Text(
                text = "0/200",
                style = WineyTheme.typography.bodyM2,
                color = WineyTheme.colors.gray_500,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .align(Alignment.End)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "와인은 어떠셨어요?",
                    style = WineyTheme.typography.bodyB1,
                    color = WineyTheme.colors.gray_50,
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    repeat(5) {
                        Icon(
                            painter = painterResource(id = if (score > it) R.drawable.ic_wine_fill_30 else R.drawable.ic_wine_unfill_30),
                            contentDescription = null,
                            modifier = Modifier
                                .size(30.dp)
                                .clickable {
                                    score = it + 1
                                },
                            tint = Color.Unspecified
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "재구매 의사",
                    style = WineyTheme.typography.bodyB1,
                    color = WineyTheme.colors.gray_50,
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    NoteFeatureText(name = "있어요", enable = repurchase == true) {
                        repurchase = true
                    }
                    NoteFeatureText(name = "없어요", enable = repurchase == false) {
                        repurchase = false
                    }
                }
            }
        }

        WButton(
            text = "작성완료",
            modifier = Modifier
                .padding(bottom = 40.dp)
                .padding(horizontal = 20.dp),
            enableBackgroundColor = WineyTheme.colors.main_2,
            disableBackgroundColor = WineyTheme.colors.gray_900,
            disableTextColor = WineyTheme.colors.gray_600,
            enableTextColor = WineyTheme.colors.gray_50,
            enabled = score != 0 && repurchase != null,
            onClick = {
                // TODO 작성 완료 화면 이동
            }
        )
    }
}