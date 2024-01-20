package com.teamwiney.map.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.teamwiney.core.design.R
import com.teamwiney.map.BOTTOM_NAVIGATION_HEIGHT
import com.teamwiney.ui.theme.WineyTheme

@Composable
fun ColumnScope.WineShopEmpty() {
    Column(
        modifier = Modifier
            .weight(1f)
            .fillMaxWidth()
            .align(Alignment.CenterHorizontally)
            .padding(bottom = BOTTOM_NAVIGATION_HEIGHT),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_note_unselected),
            contentDescription = "IMG_NOTE",
            modifier = Modifier.size(112.dp)
        )
        Text(
            text = "아직 저장된 장소가 없어요 :(",
            style = WineyTheme.typography.headline,
            color = WineyTheme.colors.gray_800,
            modifier = Modifier.padding(top = 13.dp)
        )
        Text(
            text = "마음에 드는 장소를 모아봐요",
            style = WineyTheme.typography.bodyM2,
            color = WineyTheme.colors.gray_800,
            modifier = Modifier.padding(top = 6.dp)
        )
    }
}
