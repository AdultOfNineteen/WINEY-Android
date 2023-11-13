@file:OptIn(ExperimentalGlideComposeApi::class)

package com.teamwiney.notewrite

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.rememberWineyAppState
import com.teamwiney.core.design.R
import com.teamwiney.notewrite.components.NoteBackgroundSurface
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.HeightSpacerWithLine
import com.teamwiney.ui.components.NumberPicker
import com.teamwiney.ui.components.TopBar
import com.teamwiney.ui.components.WButton
import com.teamwiney.ui.components.WTextField
import com.teamwiney.ui.theme.WineyTheme

@Composable
fun NoteWineInfoMemoScreen(
    appState: WineyAppState = rememberWineyAppState(),
    viewModel: NoteWriteViewModel = hiltViewModel(),
) {

    var vintage by remember { mutableStateOf("") }
    val selectedImage = remember { mutableStateListOf<Uri?>() }

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
                modifier = Modifier.padding(vertical = 15.dp)
            ) {
                selectedImage.map {
                    GlideImage(
                        model = it,
                        contentDescription = "IMG_URL",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(WineyTheme.colors.gray_900),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            WButton(
                text = "사진 첨부하기",
                modifier = Modifier
                    .fillMaxWidth(),
                enableBackgroundColor = Color.Transparent,
                border = BorderStroke(1.dp, WineyTheme.colors.main_2),
                onClick = {
                    imagePicker.launch("image/*")
                }
            )

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
                    value = vintage,
                    onValueChange = { vintage = it },
                    modifier = Modifier
                        .padding(14.dp)
                        .fillMaxWidth()
                        .heightIn(min = 150.dp),
                    textStyle = WineyTheme.typography.captionM1,
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
                Row {
                    repeat(5) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_etc_wine_badge),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp)
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
            }
            Spacer(modifier = Modifier.weight(1f))

            WButton(
                text = "작성완료",
                modifier = Modifier
                    .padding(bottom = 40.dp),
                enableBackgroundColor = WineyTheme.colors.main_2,
                disableBackgroundColor = WineyTheme.colors.gray_900,
                disableTextColor = WineyTheme.colors.gray_600,
                enableTextColor = WineyTheme.colors.gray_50,
                onClick = {

                }
            )
        }

    }
}