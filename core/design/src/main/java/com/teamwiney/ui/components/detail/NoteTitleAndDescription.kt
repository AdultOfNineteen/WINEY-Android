package com.teamwiney.ui.components.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter.State.Empty.painter
import com.teamwiney.core.common.model.WineType.Companion.convertToNoteType
import com.teamwiney.core.design.R
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.theme.Chaviera
import com.teamwiney.ui.theme.WineyTheme


@Composable
fun NoteTitleAndDescription(
    number: Int,
    userNickname: String? = null,
    date: String,
    type: String,
    name: String,
) {
    Column(
        modifier = Modifier.padding(horizontal = 24.dp)
    ) {
        HeightSpacer(height = 20.dp)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            userNickname?.let {
                Text(
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(
                            fontFamily = Chaviera,
                            fontWeight = FontWeight.Normal,
                            fontSize = 11.sp
                        )) {
                            append("Tasted by ")

                        }
                        append("$userNickname")
                    },
                    style = WineyTheme.typography.bodyB1.copy(
                        color = WineyTheme.colors.gray_50
                    )
                )
            } ?: Text(
                text = buildAnnotatedString {
                    append("No.")
                    withStyle(SpanStyle(color = WineyTheme.colors.main_3)) {
                        append("$number")
                    }
                },
                style = WineyTheme.typography.bodyB1.copy(
                    color = WineyTheme.colors.gray_50
                )
            )

            Text(
                text = date,
                style = WineyTheme.typography.bodyB1.copy(
                    color = WineyTheme.colors.gray_50
                )
            )
        }

        HeightSpacer(height = 30.dp)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                modifier = Modifier.height(68.dp),
                text = convertToNoteType(type),
                style = WineyTheme.typography.display1,
                color = WineyTheme.colors.gray_50
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_thismooth),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
        Text(
            text = name,
            style = WineyTheme.typography.bodyB2,
            color = WineyTheme.colors.gray_500
        )
    }
}
