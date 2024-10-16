package com.teamwiney.core.common.navigation

import androidx.annotation.DrawableRes
import com.teamwiney.core.common.R


enum class TopLevelDestination(
    val label: String,
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unselectedIcon: Int,
    val route: String
) {
    Home(
        label = "홈",
        selectedIcon = R.drawable.ic_home_selected,
        unselectedIcon = R.drawable.ic_home_unselected,
        route = HomeDestinations.HOME
    ),
    Map(
        label = "지도",
        selectedIcon = R.drawable.ic_map_selected,
        unselectedIcon = R.drawable.ic_map_unselected,
        route = MapDestinations.MAP
    ),
    Note(
        label = "노트",
        selectedIcon = R.drawable.ic_note_selected,
        unselectedIcon = R.drawable.ic_note_unselected,
        route = NoteDestinations.NOTE
    ),
    MyPage(
        label = "MY",
        selectedIcon = R.drawable.ic_mypage_selected,
        unselectedIcon = R.drawable.ic_mypage_unselected,
        route = MyPageDestinations.MY_PAGE
    )
}