package com.teamwiney.notedetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
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
                Spacer(
                    modifier = Modifier
                        .background(
                            brush = Brush.radialGradient(
                                listOf(
                                    Color(noteDetail.color.toColorInt()),
                                    Color.Transparent
                                )
                            ),
                            shape = CircleShape
                        )
                        .size(41.dp)
                )

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

