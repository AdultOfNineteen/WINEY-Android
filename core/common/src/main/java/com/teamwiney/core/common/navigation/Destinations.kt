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
    const val WINE_DETAIL = "detail"
    const val WINE_TIP = "wineTip"
    const val WINE_TIP_DETAIL = "wineTIpDetail"

    object Analysis {
        const val ROUTE = "analysis"

        const val START = "analysisStart"
        const val RESULT = "analysisResult"
    }
}

object MapDestinations {
    const val ROUTE = "mapRoute"

    const val MAP = "map"
}

object NoteDestinations {
    const val ROUTE = "noteRoute"

    const val NOTE = "note"
    const val FILTER = "filter"
    const val DETAIL = "detail"

    object Write {
        const val ROUTE = "noteWrite"

        const val SEARCH_WINE = "searchWine"
        const val SELECT_WINE = "selectWine"
        const val INFO_LEVEL = "infoLevel"
        const val INFO_MEMO = "infoMemo"
        const val INFO_COLOR_SMELL = "infoColorSmell"
        const val INFO_STANDARD_SMELL = "infoStandardSmell"
        const val INFO_FLAVOR = "infoFlavor"
        const val INFO_VINTAGE_AND_PRICE = "infoVintageAndPrice"
        const val INFO_STANDARD_FLAVOR = "infoStandardFlavor"
    }

}

object MyPageDestinations {
    const val ROUTE = "myPageRoute"

    const val MY_PAGE = "myPage"
}