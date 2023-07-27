package com.teamwiney.core_design_system.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp

data class WineyTypography(
    val largeTitle: TextStyle = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 39.sp,
        letterSpacing = 0.sp,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        ),
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val title1: TextStyle = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 26.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        ),
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val title2: TextStyle = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 25.sp,
        letterSpacing = 0.sp,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        ),
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val headline: TextStyle = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 23.sp,
        letterSpacing = 0.sp,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        ),
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val subhead: TextStyle = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        ),
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val bodyB1: TextStyle = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 17.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        ),
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val bodyM1: TextStyle = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 17.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        ),
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val bodyB2: TextStyle = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 19.sp,
        letterSpacing = 0.sp,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        ),
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val bodyM2: TextStyle = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 19.sp,
        letterSpacing = 0.sp,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        ),
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val captionB1: TextStyle = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 13.sp,
        lineHeight = 17.sp,
        letterSpacing = 0.sp,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        ),
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val captionM1: TextStyle = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp,
        lineHeight = 17.sp,
        letterSpacing = 0.sp,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        ),
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val captionM2: TextStyle = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.sp,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        ),
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )
)

val LocalTypography = staticCompositionLocalOf { wineyFonts() }
