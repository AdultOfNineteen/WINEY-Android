package com.teamwiney.core_design_system.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.teamwiney.core_design_system.R

val Pretendard = FontFamily(
    Font(R.font.pretendard_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.pretendard_regular, FontWeight.Normal, FontStyle.Normal), // Regular을 Normal로 취급
    Font(R.font.pretendard_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.pretendard_semi_bold, FontWeight.SemiBold, FontStyle.Normal),
)

val Jalnan = Font(R.font.jalnan, FontWeight.Normal, FontStyle.Normal)

val Display1 = TextStyle(
    fontFamily = Pretendard,
    fontWeight = FontWeight.Normal,
    fontSize = 42.sp,
)

val Display2 = TextStyle(
    fontFamily = Pretendard,
    fontWeight = FontWeight.Bold,
    fontSize = 28.sp,
)

val Display3 = TextStyle(
    fontFamily = Pretendard,
    fontWeight = FontWeight.Bold,
    fontSize = 26.sp,
)

val Header1 = TextStyle(
    fontFamily = Pretendard,
    fontWeight = FontWeight.Bold,
    fontSize = 22.sp,
)

val Header2 = TextStyle(
    fontFamily = Pretendard,
    fontWeight = FontWeight.Bold,
    fontSize = 20.sp,
)

// TODO 다음과 같은 이유때문에 일단 여기까지만 만들어 놓겠습니다.
//  1. 폰트를 관리하는 더 좋은 방법이 있을지 모름
//  2. 디자인이 아직 픽스되지 않았음
// 동천이 추가해주세요.
