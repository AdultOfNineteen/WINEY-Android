package com.teamwiney.mypage

import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.model.WineGrade
import com.teamwiney.mypage.components.MyPageGradeProgressBar
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.HeightSpacerWithLine
import com.teamwiney.ui.theme.WineyTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MyPageScreen(
    appState: WineyAppState,
    viewModel: MyPageViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val effectFlow = viewModel.effect

    val context = LocalContext.current

    LaunchedEffect(true) {
        effectFlow.collectLatest { effect ->
            when (effect) {
                is MyPageContract.Effect.NavigateTo -> {
                    appState.navigate(effect.destination, effect.navOptions)
                }

                is MyPageContract.Effect.ShowSnackBar -> {
                    appState.showSnackbar(effect.message)
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WineyTheme.colors.background_1)
            .verticalScroll(rememberScrollState())
    ) {
        MyProfileAppBar()
        MyPageProfile(
            name = "김희연"
        )

        MyPageGradeProgressBar(
            noteCount = uiState.noteCount,
            gradeData = uiState.wineGradeStandard
        )
        HeightSpacer(height = 20.dp)

        MyPageGrade(
            currentGrade = uiState.currentGrade,
            expectedNextMonthGrade = uiState.expectedMonthGrade,
            remainingNoteCount = maxOf(0,
                uiState.wineGradeStandard.find { it.name == uiState.expectedMonthGrade }
                    ?.minCount?.minus(uiState.noteCount) ?: 0)
        )
        
        HeightSpacer(height = 15.dp)
        MyProfileMenuItem(menu = "서비스 이용약관") {

        }
        HeightSpacer(height = 5.dp)
        MyProfileMenuItem(menu = "개인정보 처리방침") {

        }

        HeightSpacer(height = 21.dp)
        HeightSpacerWithLine(color = WineyTheme.colors.gray_900)
        HeightSpacer(height = 7.dp)

        MyProfileMenuItem(menu = "1:1 문의") {
            
        }
        HeightSpacer(height = 5.dp)
        MyProfileMenuItem(menu = "FAQ") {
            val builder = CustomTabsIntent.Builder()

            builder.setShowTitle(true)
            builder.setInstantAppsEnabled(true)

            val customBuilder = builder.build()
            customBuilder.launchUrl(context, Uri.parse("https://www.notion.so/FAQ-1671bf54033440d2aef23189c4754a45?pvs=4"))
        }
        MyProfileAppVersionItem()
    }
}

@Composable
fun MyProfileAppBar() {
    Text(
        modifier = Modifier
            .padding(
                horizontal = 24.dp,
                vertical = 16.dp
            )
            .statusBarsPadding(),
        text = "마이페이지",
        style = WineyTheme.typography.title1.copy(
            color = WineyTheme.colors.gray_50
        )
    )
}

@Preview
@Composable
fun MyPageProfile(
    name: String = "김희연"
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 24.dp,
                vertical = 20.dp
            ),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(
                    color = WineyTheme.colors.gray_900,
                    shape = CircleShape
                )
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = com.teamwiney.core.design.R.drawable.ic_user_profile),
                contentDescription = "IMG_USER_PROFILE",
                tint = Color.Unspecified
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(WineyTheme.colors.main_3)) {
                            append(name)
                        }
                        append("님")
                    },
                    style = WineyTheme.typography.title2.copy(
                        color = WineyTheme.colors.gray_50
                    )
                )

                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = com.teamwiney.core.design.R.drawable.ic_arrow_right),
                    contentDescription = "IC_ARROW_RIGHT",
                    tint = Color.Unspecified
                )
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { },
                colors = ButtonDefaults.buttonColors(
                    containerColor = WineyTheme.colors.background_1
                ),
                shape = RoundedCornerShape(24.dp),
                border = BorderStroke(
                    width = 1.dp,
                    color = WineyTheme.colors.gray_400
                ),
                contentPadding = PaddingValues(vertical = 9.dp)
            ) {
                Text(
                    text = "WINEY 뱃지",
                    style = WineyTheme.typography.captionB1.copy(
                        color = WineyTheme.colors.gray_400
                    )
                )
            }
        }
    }
}

@Composable
fun MyPageGrade(
    currentGrade: WineGrade,
    expectedNextMonthGrade: WineGrade,
    remainingNoteCount: Int
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .border(
                width = 1.dp,
                color = WineyTheme.colors.gray_800,
                shape = RoundedCornerShape(12.dp)
            )
            .background(
                shape = RoundedCornerShape(12.dp),
                color = WineyTheme.colors.background_1,
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 30.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                Text(
                    text = currentGrade.name,
                    style = WineyTheme.typography.bodyB2.copy(
                        color = WineyTheme.colors.gray_400
                    )
                )

                Text(
                    text = buildAnnotatedString {
                        append("${expectedNextMonthGrade.name}까지 테이스팅 ")
                        withStyle(
                            style = SpanStyle(
                                color = WineyTheme.colors.main_3
                            )
                        ) {
                            append("노트 ${remainingNoteCount}번")
                        }
                    },
                    style = WineyTheme.typography.captionM2.copy(
                        color = WineyTheme.colors.gray_700
                    )
                )
            }

            Button(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = WineyTheme.colors.gray_900
                ),
                contentPadding = PaddingValues(
                    horizontal = 10.dp,
                    vertical = 9.dp
                )
            ) {
                Text(
                    text = "등급기준",
                    style = WineyTheme.typography.captionB1.copy(
                        color = WineyTheme.colors.gray_400
                    )
                )
            }
        }
    }
}

@Composable
fun MyProfileMenuItem(
    menu: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 24.dp,
                vertical = 12.dp
            )
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = menu,
            style = WineyTheme.typography.bodyB1.copy(
                color = WineyTheme.colors.gray_400
            )
        )
        
        Icon(
            modifier = Modifier
                .size(24.dp)
                .clickable {
                    onClick()
                },
            painter = painterResource(id = com.teamwiney.core.design.R.drawable.ic_arrow_right),
            contentDescription = "IC_ARROW_RIGHT",
            tint = Color.Unspecified
        )
    }
}

@Composable
fun MyProfileAppVersionItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 24.dp,
                vertical = 13.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "앱버전",
            style = WineyTheme.typography.captionB1.copy(
                color = WineyTheme.colors.gray_800
            )
        )

        Text(
            text = "1.0",
            style = WineyTheme.typography.captionB1.copy(
                color = WineyTheme.colors.gray_800
            )
        )
    }
}