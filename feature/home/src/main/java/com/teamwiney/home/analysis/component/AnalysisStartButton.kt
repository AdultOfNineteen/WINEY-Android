package com.teamwiney.home.analysis.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamwiney.ui.components.drawColoredShadow
import com.teamwiney.ui.theme.WineyTheme


@Preview
@Composable
fun AnalysisStartButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .drawColoredShadow(
                color = Color(0xFF4B2EFF),
                cornerRadius = 100.dp
            ),
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(
            containerColor = WineyTheme.colors.main_1
        ),
        contentPadding = PaddingValues(
            start = 73.dp,
            end = 73.dp,
            top = 16.dp,
            bottom = 16.dp
        ),
    ) {
        Text(
            text = "분석하기",
            style = WineyTheme.typography.bodyB2.copy(
                color = WineyTheme.colors.gray_50
            )
        )
    }
}