package com.teamwiney.notecollection

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.teamwiney.core.common.`typealias`.SheetContent
import com.teamwiney.core.design.R
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.HeightSpacerWithLine
import com.teamwiney.ui.components.home.HomeLogo
import com.teamwiney.ui.theme.WineyTheme

@Composable
fun NoteScreen(
    showBottomSheet: (SheetContent) -> Unit,
) {

    val wineList = emptyList<String>()
    val radioGroup = listOf<String>("최신순", "인기순", "가격순")
    var selectedRadioButton by remember {
        mutableStateOf("최신순")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WineyTheme.colors.background_1)
    ) {
        HomeLogo(
            onClick = {
                // 취향 분석화면 이동
            },
            hintPopupOpen = false
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(WineyTheme.colors.main_3)) {
                        append("N개")
                    }
                    append("의 노트를 작성했어요!")
                },
                color = WineyTheme.colors.gray_50,
                style = WineyTheme.typography.headline
            )
            HeightSpacerWithLine(
                modifier = Modifier.padding(vertical = 14.dp),
                color = WineyTheme.colors.gray_900
            )

            if (!wineList.isEmpty()) {
                EmptyNote()
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                    ) {
                        radioGroup.forEach { radioButton ->
                            NoteRadioButton(
                                name = radioButton,
                                isEnable = selectedRadioButton == radioButton,
                            ) {
                                selectedRadioButton = radioButton
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(WineyTheme.colors.gray_900)
                            .clickable {
                                showBottomSheet {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(WineyTheme.colors.gray_950)
                                            .padding(
                                                start = 24.dp,
                                                end = 24.dp,
                                                top = 10.dp,
                                                bottom = 20.dp
                                            )
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .heightIn(max = 500.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text(
                                                text = "와인종류",
                                                color = WineyTheme.colors.gray_400,
                                                style = WineyTheme.typography.bodyB2
                                            )

                                            Text(
                                                text = "생산지",
                                                color = WineyTheme.colors.gray_400,
                                                style = WineyTheme.typography.bodyB2
                                            )
                                        }

                                        Column(modifier = Modifier.fillMaxWidth()) {
                                            HeightSpacerWithLine(
                                                color = WineyTheme.colors.gray_900,
                                                modifier = Modifier.padding(bottom = 20.dp)
                                            )
                                            Row(modifier = Modifier.fillMaxWidth()) {
                                                Text(text = "필터 초기화")
                                                Text(text = "옵션 적용하기")
                                            }

                                        }
                                    }

                                }
                            }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_filter_24),
                            contentDescription = "IC_FILTER",
                            modifier = Modifier
                                .padding(horizontal = 8.dp, vertical = 5.dp)
                                .size(24.dp),
                            tint = WineyTheme.colors.gray_50
                        )
                    }
                }
                HeightSpacer(height = 18.dp)
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(21.dp),
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    items(7) {
                        NoteWineCard()
                    }

                }
            }
        }
    }
}

@Composable
private fun NoteWineCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(7.dp))
                .border(
                    BorderStroke(
                        1.dp, brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF9671FF),
                                Color(0x109671FF),
                            )
                        )
                    ),
                    RoundedCornerShape(7.dp)
                )
                .background(WineyTheme.colors.gray_800)
        )
        HeightSpacer(height = 10.dp)
        Text(
            text = "캄포 마리니 어쩌구저쩌구ㅁㄴㅇㅁㄴㅇㅁㄴㅇ",
            color = WineyTheme.colors.gray_50,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth(),
            style = WineyTheme.typography.captionB1,
        )
        HeightSpacer(height = 2.dp)
        Text(
            text = "이탈리아 / 5점",
            color = WineyTheme.colors.gray_700,
            style = WineyTheme.typography.captionM2
        )
    }
}

@Composable
private fun NoteRadioButton(
    name: String,
    isEnable: Boolean = true,
    onClick: () -> Unit
) {
    Text(
        text = name,
        color = if (isEnable) WineyTheme.colors.gray_50 else WineyTheme.colors.gray_700,
        style = WineyTheme.typography.captionB1,
        modifier = Modifier
            .clip(RoundedCornerShape(40.dp))
            .background(if (isEnable) WineyTheme.colors.main_2 else WineyTheme.colors.gray_900)
            .border(
                BorderStroke(
                    1.dp,
                    if (isEnable) WineyTheme.colors.main_2 else WineyTheme.colors.gray_700
                ),
                RoundedCornerShape(40.dp)
            )
            .padding(10.dp)
            .clickable {
                onClick()
            }
    )
}


@Composable
private fun ColumnScope.EmptyNote() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            verticalArrangement = Arrangement.spacedBy(
                14.dp,
                Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.mipmap.img_note_empty),
                contentDescription = "IMG_NOTE_EMPTY"
            )
            Text(
                text = "아직 노트가 없어요!\n노트를 작성해 주세요:)",
                style = WineyTheme.typography.headline,
                color = WineyTheme.colors.gray_800,
                textAlign = TextAlign.Center
            )
        }
    }
}