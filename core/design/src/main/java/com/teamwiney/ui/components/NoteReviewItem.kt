package com.teamwiney.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Colors
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamwiney.core.design.R
import com.teamwiney.ui.theme.WineyTheme

@Composable
fun NoteReviewItem(
    nickName: String,
    date: String,
    rating: Int,
    buyAgain: Boolean,
    navigateToNoteDetail: () -> Unit
) {
    Surface(
        modifier = Modifier.clickable {
            navigateToNoteDetail()
        },
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    nickName,
                    style = WineyTheme.typography.bodyB1.copy(
                        color = WineyTheme.colors.gray_50
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Text(
                    date,
                    style = WineyTheme.typography.captionM2.copy(
                        color = WineyTheme.colors.gray_700
                    )
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "$rating / ${if (buyAgain) "재구매" else "재구매X"}",
                    style = WineyTheme.typography.bodyM1.copy(
                        color = WineyTheme.colors.gray_400
                    )
                )

                Spacer(modifier = Modifier.width(10.dp))

                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.ic_arrow_right),
                    contentDescription = "IC_ARROW_RIGHT",
                    tint = Color.Unspecified
                )
            }
        }
    }
}

@Preview
@Composable
fun NoteReviewItemPreview() {
    WineyTheme {
        NoteReviewItem(
            nickName = "김와인와잉완와ㅣㅇ오나외ㅏㄴ오나이ㅗ니ㅏㅓㅇ라ㅣㅓㄴㅇㅁ리ㅓㅁㄴ어;ㄹ;ㅓㅇㅁㄴㄹ;ㅓㄴㅁㅇ;러",
            date = "2021.10.10",
            rating = 5,
            buyAgain = true,
            navigateToNoteDetail = {}
        )
    }
}