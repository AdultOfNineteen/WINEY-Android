package com.teamwiney.ui.components.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.teamwiney.ui.components.HintPopUp
import com.teamwiney.ui.theme.WineyTheme


@Composable
fun HomeLogo(
    onClick: () -> Unit,
    hintPopupOpen: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(
                horizontal = 24.dp,
                vertical = 18.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier,
            text = "WINEY",
            style = WineyTheme.typography.display2.copy(
                color = WineyTheme.colors.gray_400
            )
        )

        Box {
            var buttonHeight by remember { mutableIntStateOf(0) }
            val density = LocalDensity.current

            AnalysisButton(
                modifier = Modifier.onGloballyPositioned { coordinates ->
                    buttonHeight = density.run { coordinates.size.height / 2 + 6.dp.roundToPx() }
                },
                onClick = onClick
            )
            if (hintPopupOpen) {
                HintPopUp(
                    offset = IntOffset(0, buttonHeight),
                    text = "나에게 맞는 와인을 분석해줘요!"
                )
            }
        }
    }
}