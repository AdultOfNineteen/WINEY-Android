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

// TODO 다음과 같은 이유때문에 일단 여기까지만 만들어 놓겠습니다.
//  1. 폰트를 관리하는 더 좋은 방법이 있을지 모름
//  2. 디자인이 아직 픽스되지 않았음
// 동천이 추가해주세요.
