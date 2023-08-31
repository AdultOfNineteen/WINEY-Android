package com.teamwiney.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.teamwiney.ui.theme.WineyTheme

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    content: @Composable RowScope.() -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        shadowElevation = 10.dp,
        color = backgroundColor,
        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .selectableGroup()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 45.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                content()
            }
        }
    }
}


@Composable
fun BottomNavigationItem(
    modifier: Modifier = Modifier,
    label: String,
    selected: Boolean,
    @DrawableRes selectedIcon: Int,
    @DrawableRes unselectedIcon: Int,
    selectedContentColor: Color,
    unselectedContentColor: Color,
    onClick: () -> Unit
) {
    val contentColor = if (selected) selectedContentColor else unselectedContentColor

    Surface(
        modifier = modifier,
        color = Color.Transparent,
        onClick = onClick,
        interactionSource = NoRippleInteractionSource()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = if (selected) selectedIcon else unselectedIcon),
                contentDescription = label,
                tint = Color.Unspecified
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = label,
                style = WineyTheme.typography.captionB1,
                color = contentColor
            )
        }
    }
}

