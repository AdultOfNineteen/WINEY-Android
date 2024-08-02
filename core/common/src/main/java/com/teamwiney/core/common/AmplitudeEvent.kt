package com.teamwiney.core.common

enum class AmplitudeEvent {
    HOME_ENTER,
    WINE_DETAIL_CLICK,
    ANALYZE_BUTTON_CLICK,
    TIP_POST_CLICK;

    val eventName: String
        get() = name
}