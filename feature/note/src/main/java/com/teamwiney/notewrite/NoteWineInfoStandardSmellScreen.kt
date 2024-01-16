package com.teamwiney.notewrite

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.rememberWineyAppState
import com.teamwiney.core.design.R
import com.teamwiney.notedetail.component.NoteFeatureText
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.TopBar
import com.teamwiney.ui.theme.WineyTheme

private val redWineSmell = mapOf(
    "카베르네 소비뇽" to listOf("계피", "고추", "초콜릿", "나무"),
    "메를로" to listOf("딸기", "체리", "라즈베리", "오렌지", "자두", "장미", "초콜릿"),
    "피노 누아" to listOf("딸기", "체리", "나무", "흙", "버섯"),
    "시라" to listOf("블랙베리, 오렌지, 자두", "후추"),
    "진판델" to listOf("라즈베리", "블랙체리", "초콜릿", "후추"),
    "가메" to listOf("딸기", "체리", "자두", "꽃", "계피"),
    "그르나슈" to listOf("자두", "흙", "커피", "후추", "매운향"),
    "산지오배제" to listOf("딸기", "체리", "블랙베리", "허브", "흙", "담배", "연기 매운향"),
    "템프라니오" to listOf("나무", "흙", "버섯")
)

private val whiteWineSmell = mapOf(
    "사르도네" to listOf("레몬", "배", "사과", "파인애플", "멜론", "바닐라"),
    "소비뇽블랑" to listOf("레몬", "자몽", "허브향", "풀향", "연기", "돌"),
    "슈냉 블랑" to listOf("레몬", "배", "복숭아", "멜론", "샐러리"),
    "게뷔르츠트레미너" to listOf("리치", "패션후르츠", "장미"),
    "리스링" to listOf("사과", "살구", "복숭아", "꿀"),
    "세미용" to listOf("레몬", "풀", "버터", "땅콩"),
    "비오니에" to listOf("살구", "꽃")
)


@Preview
@Composable
fun NoteWineInfoStandardSmellScreen(
    appState: WineyAppState = rememberWineyAppState(),
    viewModel: NoteWriteViewModel = hiltViewModel(),
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WineyTheme.colors.background_1)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        TopBar {
            appState.navController.navigateUp()
        }
        Column(
            modifier = Modifier
                .padding(start = 24.dp, end = 24.dp, bottom = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            HeightSpacer(height = 20.dp)
            Text(
                text = buildAnnotatedString {
                    append("와인 품종에 따른\n")
                    withStyle(style = SpanStyle(WineyTheme.colors.main_2)) {
                        append("대표적인 향을")
                    }
                    append(" 느껴보세요")
                },
                color = WineyTheme.colors.gray_50,
                style = WineyTheme.typography.title2
            )
            HeightSpacer(height = 30.dp)

            Column(verticalArrangement = Arrangement.spacedBy(50.dp)) {
                Column {
                    Text(
                        text = "레드 와인",
                        style = WineyTheme.typography.headline,
                        color = WineyTheme.colors.gray_500,
                        modifier = Modifier.padding(bottom = 20.dp)
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(25.dp)
                    ) {
                        for ((key, value) in redWineSmell) {
                            WineStandardFlavor(varietal = key, smell = value)
                        }
                    }
                }

                Column {
                    Text(
                        text = "화이트 와인",
                        style = WineyTheme.typography.headline,
                        color = WineyTheme.colors.gray_500,
                        modifier = Modifier.padding(bottom = 20.dp)
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(25.dp)
                    ) {
                        for ((key, value) in whiteWineSmell) {
                            WineStandardFlavor(varietal = key, smell = value)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun WineStandardFlavor(
    varietal: String,
    smell: List<String>
) {

    var tagVisible by remember { mutableStateOf(false) }

    Column {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                tagVisible = !tagVisible
            }
        ) {
            Text(
                text = varietal,
                style = WineyTheme.typography.subhead,
                color = if (tagVisible) WineyTheme.colors.gray_300 else WineyTheme.colors.gray_500
            )
            Icon(
                painter = painterResource(
                    id = if (tagVisible) R.drawable.ic_dropdown_up_15 else R.drawable.ic_dropdown_down_15
                ),
                tint = if (tagVisible) WineyTheme.colors.gray_600 else WineyTheme.colors.gray_300,
                contentDescription = "dropdown",
            )
        }
        AnimatedVisibility(
            visible = tagVisible,
            enter = slideInVertically { -it } + fadeIn(),
            exit = slideOutVertically { -it } + fadeOut(),
        ) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier.padding(top = 12.dp)
            ) {
                items(smell) {
                    NoteFeatureText(name = it)
                }
            }
        }
    }
}
