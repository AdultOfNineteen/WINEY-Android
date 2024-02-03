package com.teamwiney.analysis.component.pagercontent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.teamwiney.data.network.model.response.Top3Type
import com.teamwiney.ui.components.ChartData
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.PieChart
import com.teamwiney.ui.theme.WineyTheme

@Composable
fun WineTypeContent(
    progress: Float,
    types: List<Top3Type>,
    totalWineCount: Int,
    buyAgainCount: Int,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HeightSpacer(height = 22.dp)
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = WineyTheme.colors.gray_700)) {
                    append("지금까지 ")
                }
                append("${totalWineCount}개의 와인을 마셨고\n${buyAgainCount}개의 와인에 대해 재구매")
                withStyle(style = SpanStyle(color = WineyTheme.colors.gray_700)) {
                    append(" 의향이 있어요!")
                }
            },
            style = WineyTheme.typography.captionM1,
            color = WineyTheme.colors.gray_50,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 56.dp),
            textAlign = TextAlign.Center
        )

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            val chartData = types.mapIndexed { index, type ->
                val labelColor = when (index) {
                    0 -> WineyTheme.colors.main_1
                    1 -> WineyTheme.colors.gray_800
                    else -> WineyTheme.colors.gray_900
                }

                val labelStyle = when (index) {
                    0 -> WineyTheme.typography.title2.copy(
                        color = WineyTheme.colors.gray_50
                    )
                    1 -> WineyTheme.typography.bodyB2.copy(
                        color = WineyTheme.colors.gray_800
                    )
                    else -> WineyTheme.typography.captionB1.copy(
                        color = WineyTheme.colors.gray_900
                    )
                }

                ChartData(
                    label = type.type,
                    color = labelColor,
                    value = type.percent.toFloat(),
                    textStyle = labelStyle
                )
            }

            PieChart(
                modifier = Modifier.fillMaxSize(0.9f),
                progress = progress,
                data = chartData
            )
        }
    }
}