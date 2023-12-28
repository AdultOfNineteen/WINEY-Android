package com.teamwiney.notedetail.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.teamwiney.core.design.R
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.theme.WineyTheme

@Composable
fun NoteDeleteBottomSheet(
    modifier: Modifier = Modifier,
    containerColor: Color = WineyTheme.colors.gray_950,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = containerColor,
                shape = RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp)
            )
            .padding(start = 24.dp, end = 24.dp, top = 10.dp, bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
                .width(66.dp)
                .height(5.dp)
                .background(
                    color = WineyTheme.colors.gray_900,
                    shape = RoundedCornerShape(6.dp)
                )
        )
        HeightSpacer(height = 20.dp)
        Image(
            painter = painterResource(id = R.mipmap.img_analysis_note),
            contentDescription = null
        )
        HeightSpacer(height = 16.dp)
        Text(
            text = buildAnnotatedString {
                append("테이스팅 노트를 삭제하시겠어요?\n")
                withStyle(style = SpanStyle(WineyTheme.colors.gray_600)) {
                    append("삭제한 노트는 복구할 수 없어요 :(")
                }
            },
            style = WineyTheme.typography.bodyB1,
            color = WineyTheme.colors.gray_200,
            textAlign = TextAlign.Center
        )
        HeightSpacer(height = 66.dp)
        BottomSheetSelectionButton(
            onConfirm = { onConfirm() },
            onCancel = { onCancel() }
        )
        HeightSpacer(height = 20.dp)
    }
}

@Composable
private fun BottomSheetSelectionButton(
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp),
        color = WineyTheme.colors.gray_700
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(67.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            color = Color.Transparent,
            onClick = { onCancel() }
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "아니오",
                    style = WineyTheme.typography.bodyB1,
                    color = WineyTheme.colors.gray_100
                )
            }
        }
        VerticalDivider(
            modifier = Modifier
                .width(1.dp)
                .fillMaxHeight()
                .padding(vertical = 21.dp),
            color = WineyTheme.colors.gray_700
        )
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            color = Color.Transparent,
            onClick = { onConfirm() }
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "네, 지울래요",
                    style = WineyTheme.typography.bodyB1,
                    color = WineyTheme.colors.gray_600
                )
            }
        }
    }
}