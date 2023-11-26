package com.teamwiney.notewrite.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.ScoreSelector
import com.teamwiney.ui.theme.WineyTheme

@Composable
@Preview
fun WineTasteSlider(
    modifier: Modifier = Modifier.fillMaxWidth(),
    score: Int = 0,
    onValueChange: (Int) -> Unit = { },
    inactiveColor: Color = WineyTheme.colors.gray_800,
    activeColor: Color = WineyTheme.colors.main_2,
    title: String = "당도",
    subTitle: String = "단맛의 정도"
) {
    Column(modifier = modifier) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = WineyTheme.typography.bodyB1,
                color = WineyTheme.colors.gray_500
            )
            Text(
                text = "・",
                style = WineyTheme.typography.captionB1,
                color = WineyTheme.colors.gray_50
            )
            Text(
                text = subTitle,
                style = WineyTheme.typography.captionB1,
                color = WineyTheme.colors.gray_600
            )
        }
        HeightSpacer(height = 15.dp)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "낮음",
                style = WineyTheme.typography.captionM1,
                color = WineyTheme.colors.gray_600
            )
            Text(
                text = "높음",
                style = WineyTheme.typography.captionM1,
                color = WineyTheme.colors.gray_600
            )
        }
        HeightSpacer(height = 10.dp)
        ScoreSelector(
            modifier = Modifier.padding(horizontal = 5.dp),
            value = score,
            valueRange = 1..5,
            onValueChange = onValueChange,
            inactiveColor = inactiveColor,
            activeColor = activeColor
        )
    }
}