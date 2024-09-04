package com.teamwiney.notedetail.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.teamwiney.data.network.model.response.TastingNoteDetail
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.theme.WineyTheme

@Composable
fun WineMemo(noteDetail: TastingNoteDetail) {

    val context = LocalContext.current

    Column {
        Text(
            text = "Feelings",
            style = WineyTheme.typography.display2,
            color = WineyTheme.colors.gray_50,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        HeightSpacer(height = 20.dp)

        if (noteDetail.tastingNoteImage.isNotEmpty()) {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.width(14.dp))
                }
                items(noteDetail.tastingNoteImage.map { it.imgUrl }) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(it)
                            .build(),
                        contentDescription = "IMG_URL",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(WineyTheme.colors.gray_900)
                    )
                }
                item {
                    Spacer(modifier = Modifier.width(14.dp))
                }
            }
            HeightSpacer(height = 36.dp)
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .wrapContentHeight()
                .clip(RoundedCornerShape(10.dp))
                .border(
                    BorderStroke(
                        1.dp, WineyTheme.colors.gray_800
                    ),
                    RoundedCornerShape(10.dp)
                )
        ) {
            Text(
                text = noteDetail.memo.ifEmpty { "입력한 내용이 없어요!" },
                modifier = Modifier.padding(14.dp),
                style = WineyTheme.typography.captionM1,
                color = WineyTheme.colors.gray_50
            )
        }

        HeightSpacer(height = 30.dp)
    }
}
