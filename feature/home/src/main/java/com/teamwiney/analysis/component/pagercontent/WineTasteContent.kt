package com.teamwiney.analysis.component.pagercontent

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.teamwiney.core.common.model.WineType
import com.teamwiney.data.network.model.response.Taste
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.RadarChart
import com.teamwiney.ui.components.RadarData
import com.teamwiney.ui.theme.WineyTheme


@Composable
fun WineTasteContent(
    progress: Float,
    wineType: WineType,
    tastes: Taste
) {
    Log.d("WineTasteContent", "wineType: $wineType, progress: $progress, tastes: $tastes")

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeightSpacer(height = 33.dp)

        Row {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(5.dp)
                    .background(WineyTheme.colors.main_2)
            )
            Text(
                text = "선호하는 맛",
                style = WineyTheme.typography.title2,
                color = WineyTheme.colors.gray_50,
                textAlign = TextAlign.Center,
            )
        }

        Column(
            modifier = Modifier.fillMaxHeight().weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            RadarChart(
                modifier = Modifier.fillMaxWidth(0.8f),
                progress = progress,
                data = listOf(
                    RadarData("당도", tastes.sweetness.toFloat()),
                    RadarData("여운", tastes.finish.toFloat()),
                    if (wineType == WineType.SPARKLING) {
                        RadarData("탄산감", tastes.sparkling.toFloat())
                    } else {
                        RadarData("알코올", tastes.alcohol.toFloat())
                    },
                    RadarData("탄닌", tastes.tannin.toFloat()),
                    RadarData("바디", tastes.body.toFloat()),
                    RadarData("산도", tastes.acidity.toFloat())
                )
            )
        }
    }
}

