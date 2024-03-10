package com.teamwiney.mypage.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.teamwiney.core.common.model.WineGrade
import com.teamwiney.data.network.model.response.WineGradeStandard
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.theme.WineyTheme

@Composable
fun MyPageWineGradeStandardDialog(
    wineGradeStandardList: List<WineGradeStandard>,
    onClose: () -> Unit = { }
) {
    Dialog(
        onDismissRequest = { }
    ) {
        Box(
            modifier = Modifier.background(
                color = WineyTheme.colors.gray_950,
                shape = RoundedCornerShape(20.dp)
            ),
            contentAlignment = Alignment.TopEnd
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        top = 39.dp,
                        bottom = 36.dp,
                        start = 48.dp,
                        end = 48.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "WINEY 등급",
                    style = WineyTheme.typography.title1.copy(
                        color = WineyTheme.colors.gray_50
                    )
                )
                HeightSpacer(height = 10.dp)
                Text(
                    text = "직전 3개월의 테이스팅 노트 작성 개수로\n매월 1일 오전 9시에 업데이트 됩니다.",
                    style = WineyTheme.typography.captionM2.copy(
                        color = WineyTheme.colors.gray_500
                    ),
                    textAlign = TextAlign.Center
                )
                HeightSpacer(height = 40.dp)
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(26.dp)
                ) {
                    wineGradeStandardList.forEachIndexed { index, wineGradeStandard ->
                        WineGradeStandardItem(
                            grade = wineGradeStandard.name,
                            description = if (index != wineGradeStandardList.size - 1) {
                                "테이스팅 노트 ${wineGradeStandard.minCount}~${wineGradeStandard.maxCount}개 작성"
                            } else {
                                "테이스팅 노트 ${wineGradeStandard.minCount}개 작성"
                            }
                        )
                    }
                }
            }

            Box(
                modifier = Modifier.padding(top = 15.dp, end = 15.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .clickable { onClose() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(40.dp),
                        painter = painterResource(id = com.teamwiney.core.design.R.drawable.ic_close),
                        contentDescription = "IC_CLOSE",
                        tint = Color.Unspecified
                    )
                }
            }
        }
    }
}

@Composable
private fun WineGradeStandardItem(
    grade: WineGrade,
    description: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = grade.name,
            style = WineyTheme.typography.captionB1.copy(
                color = WineyTheme.colors.gray_50
            )
        )
        Text(
            text = description,
            style = WineyTheme.typography.captionM2.copy(
                color = WineyTheme.colors.gray_500
            )
        )
    }
}