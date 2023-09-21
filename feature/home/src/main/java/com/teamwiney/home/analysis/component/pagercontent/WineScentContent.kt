package com.teamwiney.home.analysis.component.pagercontent

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.theme.WineyTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun WineScentContent() {
    val animatedProgress = remember { Animatable(0f) }

    var animatedOffset by remember {
        mutableStateOf(0.dp)
    }
    val animatedPosition by animateDpAsState(
        targetValue = animatedOffset,
        animationSpec = tween(
            durationMillis = 1000,
            easing = LinearEasing
        )
    )

    var animatedOffset2 by remember {
        mutableStateOf(0.dp)
    }
    val animatedPosition2 by animateDpAsState(
        targetValue = animatedOffset2,
        animationSpec = tween(
            durationMillis = 1000,
            easing = LinearEasing
        )
    )

    LaunchedEffect(true) {
        launch {
            animatedProgress.snapTo(0f)
            animatedProgress.animateTo(1f, animationSpec = tween(1000))
        }

        while (true) {
            animatedOffset = (-4).dp
            animatedOffset2 = 2.dp
            delay(1000L)
            animatedOffset = 0.dp
            animatedOffset2 = 0.dp
            delay(1000L)
            animatedOffset = 4.dp
            animatedOffset2 = (-2).dp
            delay(1000L)
        }
    }


    Column(modifier = Modifier.fillMaxSize()) {
        HeightSpacer(height = 33.dp)

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(5.dp)
                    .background(WineyTheme.colors.main_2)
            )
            Text(
                text = "선호하는 향",
                style = WineyTheme.typography.title2,
                color = WineyTheme.colors.gray_50,
                textAlign = TextAlign.Center,
            )
        }
        HeightSpacer(height = 40.dp)

        Box(
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth().height(324.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(324 * animatedProgress.value.dp)
                        .blur(50.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Spacer(
                        modifier = Modifier
                            .size(108 * animatedProgress.value.dp)
                            .background(
                                color = Color(0xFF5123DF).copy(0.5f),
                                shape = CircleShape
                            )
                    )
                }
            }

            /** 서버에서 내려주는 값은 다음과 같음
             * 7개의 값을 내려주며 랭킹 1순위(1개), 2순위(1개), 3순위(3개), 4순위(2개)로 UI구성
            "smell": "BREAD",
            "percent": 10
            "smell": "FRUIT",
            "percent": 20
            "smell": "OAK",
            "percent": 10
             *  */

            /** 서버에서 내려주는 값은 다음과 같음
             * 7개의 값을 내려주며 랭킹 1순위(1개), 2순위(1개), 3순위(3개), 4순위(2개)로 UI구성
            "smell": "BREAD",
            "percent": 10
            "smell": "FRUIT",
            "percent": 20
            "smell": "OAK",
            "percent": 10
             *  */

            /** 서버에서 내려주는 값은 다음과 같음
             * 7개의 값을 내려주며 랭킹 1순위(1개), 2순위(1개), 3순위(3개), 4순위(2개)로 UI구성
            "smell": "BREAD",
            "percent": 10
            "smell": "FRUIT",
            "percent": 20
            "smell": "OAK",
            "percent": 10
             *  */

            /** 서버에서 내려주는 값은 다음과 같음
             * 7개의 값을 내려주며 랭킹 1순위(1개), 2순위(1개), 3순위(3개), 4순위(2개)로 UI구성
            "smell": "BREAD",
            "percent": 10
            "smell": "FRUIT",
            "percent": 20
            "smell": "OAK",
            "percent": 10
             *  */

            /** 1순위 향 */

            /** 1순위 향 */

            /** 1순위 향 */

            /** 1순위 향 */
            Text(
                text = "유칼립투스",
                style = WineyTheme.typography.title2,
                color = WineyTheme.colors.main_3,
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(y = animatedPosition)
            )

            /** 2순위 향 */

            /** 2순위 향 */

            /** 2순위 향 */

            /** 2순위 향 */
            Text(
                text = "사과꿀",
                style = WineyTheme.typography.bodyB1,
                color = WineyTheme.colors.gray_200,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .fillMaxWidth(0.3f)
                    .padding(top = 80.dp)
                    .offset(
                        y = animatedPosition2
                    ),
                textAlign = TextAlign.Start
            )

            /** 3순위 향 */

            /** 3순위 향 */

            /** 3순위 향 */

            /** 3순위 향 */
            Text(
                text = "가죽",
                style = WineyTheme.typography.bodyB1,
                color = WineyTheme.colors.gray_600,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .fillMaxWidth(0.23f)
                    .padding(top = 90.dp)
                    .offset(
                        y = animatedPosition2
                    ),
                textAlign = TextAlign.End
            )

            Text(
                text = "꿀",
                style = WineyTheme.typography.bodyB1,
                color = WineyTheme.colors.gray_600,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .fillMaxWidth(0.23f)
                    .padding(bottom = 120.dp)
                    .offset(
                        y = animatedPosition2
                    ),
                textAlign = TextAlign.End
            )

            Text(
                text = "꽃",
                style = WineyTheme.typography.bodyB1,
                color = WineyTheme.colors.gray_600,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .fillMaxWidth(0.27f)
                    .padding(bottom = 120.dp)
                    .offset(
                        y = animatedPosition
                    ),
                textAlign = TextAlign.Start
            )

            /** 4순위 향 */

            /** 4순위 향 */

            /** 4순위 향 */

            /** 4순위 향 */
            Text(
                text = "흙",
                style = WineyTheme.typography.captionB1,
                color = WineyTheme.colors.gray_800,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .fillMaxWidth(0.1f)
                    .padding(top = 20.dp)
                    .offset(
                        y = animatedPosition2
                    ),
                textAlign = TextAlign.End
            )

            Text(
                text = "바닐라",
                style = WineyTheme.typography.captionB1,
                color = WineyTheme.colors.gray_800,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .fillMaxWidth(0.17f)
                    .padding(bottom = 50.dp)
                    .offset(
                        y = animatedPosition
                    ),
                textAlign = TextAlign.Start
            )

        }
    }
}
