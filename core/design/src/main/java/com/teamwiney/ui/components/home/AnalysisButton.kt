package com.teamwiney.ui.components.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamwiney.core.design.R
import com.teamwiney.ui.components.drawColoredShadow
import com.teamwiney.ui.theme.WineyTheme

@Preview
@Composable
fun AnalysisButton(
    modifier: Modifier = Modifier,
    borderColor: Color = WineyTheme.colors.main_3,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .drawColoredShadow(
                color = WineyTheme.colors.main_3,
                cornerRadius = 25.dp
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = WineyTheme.colors.background_1
        ),
        border = BorderStroke(
            width = 1.dp,
            color = borderColor
        ),
        contentPadding = PaddingValues(
            start = 15.dp,
            end = 12.dp,
            top = 7.dp,
            bottom = 7.dp
        ),
        shape = RoundedCornerShape(25.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.ic_analysis),
                contentDescription = null,
                tint = Color.Unspecified
            )

            Spacer(modifier = Modifier.width(3.dp))

            Text(
                text = "분석하기",
                style = WineyTheme.typography.captionB1.copy(
                    color = WineyTheme.colors.main_3
                )
            )
        }
    }
}