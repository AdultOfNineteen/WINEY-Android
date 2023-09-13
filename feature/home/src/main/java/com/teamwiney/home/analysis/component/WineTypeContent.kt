package com.teamwiney.home.analysis.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.PieChart
import com.teamwiney.ui.theme.WineyTheme

@Composable
fun WineTypeContent() {
    Column(modifier = Modifier.fillMaxSize()) {

        HeightSpacer(height = 22.dp)
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = WineyTheme.colors.gray_700)) {
                    append("지금까지")
                }
                append("7개의 와인을 마셨고\n5개의 와인에 대해 재구매")
                withStyle(style = SpanStyle(color = WineyTheme.colors.gray_700)) {
                    append("의향이 있어요!")
                }
            },
            style = WineyTheme.typography.captionM1,
            color = WineyTheme.colors.gray_50,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 56.dp),
            textAlign = TextAlign.Center
        )
        HeightSpacer(height = 70.dp)
        PieChart()
    }
}