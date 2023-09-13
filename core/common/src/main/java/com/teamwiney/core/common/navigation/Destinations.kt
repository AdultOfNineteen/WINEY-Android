package com.teamwiney.core.common.navigation

object AuthDestinations {
    const val ROUTE = "authRoute"

    const val SPLASH = "splash"

    object Login {
        const val ROUTE = "loginRoute"

        const val LOGIN = "login"
    }

    object SignUp {
        const val ROUTE = "signUpRoute"

        const val PHONE = "signUpPhone"
        const val AUTHENTICATION = "signUpAuthentication"
        const val FAVORITE_TASTE = "signUpFavoriteTaste"
        const val COMPLETE = "signUpComplete"
    }
}

object HomeDestinations {
    const val ROUTE = "homeRoute"

    const val HOME = "home"
    const val DETAIL = "detail"
    const val ANAYLYSIS = "analysis"
}

object MapDestinations {
    const val ROUTE = "mapRoute"

    const val MAP = "map"
}

object NoteDestinations {
    const val ROUTE = "noteRoute"

    const val NOTE = "note"
}

object MyPageDestinations {
    const val ROUTE = "myPageRoute"

    const val MY_PAGE = "myPage"
}