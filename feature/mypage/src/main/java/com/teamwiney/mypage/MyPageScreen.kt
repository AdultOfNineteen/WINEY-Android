package com.teamwiney.mypage

import android.content.Context
import android.content.Intent
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.model.WineGrade
import com.teamwiney.core.common.navigation.MyPageDestinations
import com.teamwiney.core.common.util.Constants.FAQ_URL
import com.teamwiney.data.network.model.response.WineGradeStandard
import com.teamwiney.mypage.components.MyPageGradeProgressBar
import com.teamwiney.mypage.components.MyPageWineGradeStandardDialog
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
        viewModel.getUserWineGrade()
        viewModel.getWineGradeStandard()

        effectFlow.collectLatest { effect ->
            when (effect) {
                is MyPageContract.Effect.NavigateTo -> {
                    appState.navigate(effect.destination, effect.navOptions)
                }

                is MyPageContract.Effect.ShowSnackBar -> {
                    appState.showSnackbar(effect.message)
                }

                else -> { }
            }
        }
    }

    if (uiState.isWineGradeStandardDialogOpen) {
        MyPageWineGradeStandardDialog(wineGradeStandardList = uiState.wineGradeStandard) {
            viewModel.processEvent(MyPageContract.Event.CloseWineGradeStandardDialog)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WineyTheme.colors.background_1)
    ) {
        MyProfileAppBar()
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            MyPageProfile(
                name = "김희연",
                onProfileClick = {
                    appState.navigate(MyPageDestinations.ACCOUNT)
                },
                onWineyBadgeClick = {
                    appState.navigate(MyPageDestinations.BADGE)
                }
            )

            MyPageGradeProgressBar(
                noteCount = uiState.noteCount,
                gradeData = uiState.wineGradeStandard
            )
            HeightSpacer(height = 20.dp)

            MyPageGrade(
                gradeData = uiState.wineGradeStandard,
                currentGrade = uiState.currentGrade,
                expectedNextMonthGrade = uiState.expectedMonthGrade,
                noteCount = uiState.noteCount,
                showWineGradeStandardDialog = {
                    viewModel.processEvent(MyPageContract.Event.ShowWineGradeStandardDialog)
                }
            )

            HeightSpacer(height = 15.dp)
            MyProfileMenuItem(menu = "서비스 이용약관") {
                appState.navigate(MyPageDestinations.TERMS_OF_USE)
            }
            HeightSpacer(height = 5.dp)
            MyProfileMenuItem(menu = "개인정보 처리방침") {
                appState.navigate(MyPageDestinations.PRIVACY_POLICY)
            }

            HeightSpacer(height = 21.dp)
            HeightSpacerWithLine(color = WineyTheme.colors.gray_900)
            HeightSpacer(height = 7.dp)

            MyProfileMenuItem(menu = "1:1 문의") {
                val recipientEmail = "winey.official.kr@gmail.com"

                val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$recipientEmail"))

                try {
                    context.startActivity(Intent.createChooser(emailIntent, "이메일 앱 선택"))
                } catch (ex: android.content.ActivityNotFoundException) {
                    appState.showSnackbar("이메일을 보낼 수 있는 앱이 설치되어 있지 않습니다.")
                }
            }
            HeightSpacer(height = 5.dp)
            MyProfileMenuItem(menu = "FAQ") {
                val builder = CustomTabsIntent.Builder()

                builder.setShowTitle(true)
                builder.setInstantAppsEnabled(true)

                val customBuilder = builder.build()
                customBuilder.launchUrl(
                    context,
                    Uri.parse(FAQ_URL)
                )
            }
            MyProfileAppVersionItem()
        }
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

@Composable
fun MyPageProfile(
    name: String,
    onProfileClick: () -> Unit,
    onWineyBadgeClick: () -> Unit
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
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onProfileClick() },
                    painter = painterResource(id = com.teamwiney.core.design.R.drawable.ic_arrow_right),
                    contentDescription = "IC_ARROW_RIGHT",
                    tint = Color.Unspecified
                )
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onWineyBadgeClick() },
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
    gradeData: List<WineGradeStandard>,
    currentGrade: WineGrade,
    expectedNextMonthGrade: WineGrade,
    noteCount: Int,
    showWineGradeStandardDialog: () -> Unit
) {
    val currentGradeIdx = gradeData.indexOfFirst { it.name == currentGrade }
    val expectedNextMonthGradeIdx = gradeData.indexOfFirst { it.name == expectedNextMonthGrade }

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
                    text = if (currentGradeIdx < expectedNextMonthGradeIdx) {
                        buildAnnotatedString {
                            append("다음달 예상등급 ")
                            withStyle(
                                style = SpanStyle(
                                    color = WineyTheme.colors.main_3
                                )
                            ) {
                                append("$expectedNextMonthGrade")
                            }
                        }
                    } else {
                        val nextGrade = gradeData.getOrNull(currentGradeIdx + 1)?.name ?: WineGrade.GLASS
                        val nextGradeMinCount = gradeData.getOrNull(currentGradeIdx + 1)?.minCount ?: 0
                        val remainingNoteCount = nextGradeMinCount - noteCount

                        buildAnnotatedString {
                            append("${nextGrade}까지 테이스팅 ")
                            withStyle(
                                style = SpanStyle(
                                    color = WineyTheme.colors.main_3
                                )
                            ) {
                                append("노트 ${remainingNoteCount}번")
                            }
                        }
                    },
                    style = WineyTheme.typography.captionM2.copy(
                        color = WineyTheme.colors.gray_700
                    )
                )
            }

            Button(
                onClick = { showWineGradeStandardDialog() },
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

private fun sendEmail(
    context: Context
) {
    val recipientEmail = "winey.official.kr@gmail.com"

    val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$recipientEmail"))

    try {
        context.startActivity(Intent.createChooser(emailIntent, "이메일 앱 선택"))
    } catch (ex: android.content.ActivityNotFoundException) {
        // 이메일 앱이 설치되어 있지 않을 경우에 대한 예외 처리
    }
}