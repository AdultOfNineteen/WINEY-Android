package com.teamwiney.mypage.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.teamwiney.data.network.model.response.WineBadge
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.HeightSpacerWithLine
import com.teamwiney.ui.theme.WineyTheme

@Composable
fun MyPageBadgeDetailBottomSheet(
    modifier: Modifier = Modifier,
    wineBadge: WineBadge,
    containerColor: Color = WineyTheme.colors.gray_950,
    onConfirm: () -> Unit
) {
    val context = LocalContext.current
    val isAcquired = wineBadge.acquiredAt != null

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = containerColor,
                shape = RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp)
            )
            .padding(start = 24.dp, end = 24.dp, top = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
                .width(66.dp)
                .height(5.dp)
                .background(
                    color = WineyTheme.colors.gray_900,
                    shape = RoundedCornerShape(6.dp)
                )
        )
        HeightSpacer(height = 30.dp)
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(if (isAcquired) wineBadge.imgUrl else wineBadge.unActivatedImgUrl)
                .build(),
            contentDescription = "BADGE_IMAGE",
            modifier = Modifier.size(112.dp),
            contentScale = ContentScale.Crop
        )
        HeightSpacer(height = 10.dp)
        Text(
            text = wineBadge.name,
            style = WineyTheme.typography.bodyB1.copy(
                color = WineyTheme.colors.gray_50
            )
        )
        wineBadge.acquiredAt?.let { acquiredAt ->
            HeightSpacer(height = 4.dp)
            Text(
                text = acquiredAt,
                style = WineyTheme.typography.bodyM2.copy(
                    color = WineyTheme.colors.gray_600
                )
            )
        }
        HeightSpacer(height = 13.dp)
        Text(
            text = if (wineBadge.acquiredAt != null) {
                wineBadge.description
            } else {
               wineBadge.acquisitionMethod
            },
            style = WineyTheme.typography.bodyM2.copy(
                color = WineyTheme.colors.gray_600
            ),
            textAlign = TextAlign.Center
        )
        HeightSpacer(height = 30.dp)
        HeightSpacerWithLine(color = WineyTheme.colors.gray_700)
        Surface(
            color = Color.Transparent,
            onClick = { onConfirm() }
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 23.dp, bottom = 25.dp),
                text = "확인",
                textAlign = TextAlign.Center,
                style = WineyTheme.typography.headline.copy(
                    color = WineyTheme.colors.gray_50
                )
            )
        }
    }
}