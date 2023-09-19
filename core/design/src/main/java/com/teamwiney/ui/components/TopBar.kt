package com.teamwiney.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamwiney.core.design.R
import com.teamwiney.ui.theme.WineyTheme

@Preview
@Composable
fun TopBar(
    content: String = "",
    annotatedContent: AnnotatedString = buildAnnotatedString {  },
    @DrawableRes leadingIcon: Int? = R.drawable.ic_back_arrow_48,
    @DrawableRes trailingIcon: Int? = null,
    leadingIconOnClick: () -> Unit = {},
    trailingIconOnClick: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (leadingIcon != null) {
                Icon(
                    painter = painterResource(id = leadingIcon),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable { leadingIconOnClick() }
                )
            }

            if (trailingIcon != null) {
                Icon(
                    painter = painterResource(id = trailingIcon),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable { trailingIconOnClick() }
                )
            }
        }

        Text(
            text = content,
            style = WineyTheme.typography.title2.copy(
                fontWeight = FontWeight.Bold,
                color = WineyTheme.colors.gray_50
            )
        )

        Text(
            text = annotatedContent,
            style = WineyTheme.typography.title2.copy(
                fontWeight = FontWeight.Bold,
                color = WineyTheme.colors.gray_50
            )
        )
    }
}