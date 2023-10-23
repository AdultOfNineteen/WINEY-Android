package com.teamwiney.notedetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.teamwiney.data.network.model.response.TastingNoteDetail
import com.teamwiney.ui.theme.WineyTheme


@Composable
fun WineSmellFeature(noteDetail: TastingNoteDetail) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "Feature",
            style = WineyTheme.typography.display2,
            color = WineyTheme.colors.gray_50
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row {
                Box(
                    modifier = Modifier
                        .size(41.dp)
                        .blur(5.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(5.dp)
                            .clip(CircleShape)
                            .background(
                                color = Color(0xFFEC7CA4).copy(0.5f),
                                shape = CircleShape
                            )
                    )
                }

                Spacer(
                    modifier = Modifier
                        .padding(horizontal = 7.dp)
                        .width(1.dp)
                        .height(43.dp)
                        .background(WineyTheme.colors.gray_900)
                )
            }

            LazyRow(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                items(noteDetail.smellKeywordList) {
                    NoteFeatureText(
                        name = it,
                    )
                }
            }
        }
    }
}

