package com.teamwiney.notewrite

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamwiney.notewrite.components.NoteBackgroundSurface
import com.teamwiney.ui.components.WButton
import com.teamwiney.ui.theme.WineyTheme

@Composable
@Preview
fun NoteWriteCompleteScreen(
) {

    Box(
        modifier = Modifier
            .background(WineyTheme.colors.background_1)
            .navigationBarsPadding()
            .statusBarsPadding()
    ) {
        NoteBackgroundSurface(modifier = Modifier.fillMaxSize())
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
            ) {

                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth(0.6f)
                        .fillMaxHeight(0.5f)
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
                        .clip(RoundedCornerShape(7.dp))
                        .background(
                            color = Color(0x993F3F3F),
                            shape = RoundedCornerShape(5.dp)
                        ),
                ) {
                    Box(modifier = Modifier.fillMaxSize()
                        .background(WineyTheme.colors.gray_900.copy(alpha = 0.3f))
                        .blur(30.dp)
                        .alpha(0.4f)
                    ) {

                    }

                    Text(
                        text = "테이스팅 노트 작성이\n완료 되었어요!",
                        style = WineyTheme.typography.bodyB1.copy(color = WineyTheme.colors.gray_50),
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 48.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }

            WButton(
                text = "확인",
                modifier = Modifier
                    .padding(bottom = 40.dp)
                    .padding(horizontal = 24.dp)
                    .align(Alignment.CenterHorizontally),
                enableBackgroundColor = WineyTheme.colors.main_1,
                enableTextColor = WineyTheme.colors.gray_50,
                onClick = {

                }
            )
        }
    }
}

