package com.teamwiney.data.network.model.response

import android.net.Uri

data class TastingNoteDetail(
    val noteId: Long,
    val tastingNoteNo: Int,
    val noteDate: String,
    val wineType: String,
    val wineName: String,
    val region: String,
    val star: Int,
    val vintage: Int?,
    val color: String,
    val buyAgain: Boolean,
    val varietal: String,
    val officialAlcohol: Int?,
    val price: Int?,
    val smellKeywordList: List<String>,
    val myWineTaste: MyWineTaste,
    val defaultWineTaste: WineTaste,
    val tastingNoteImage: List<TastingNoteImage>,
    val memo: String,
    val public: Boolean
) {
    companion object {
        fun default() = TastingNoteDetail(
            noteId = 0L,
            tastingNoteNo = 0,
            wineName = "-",
            noteDate = "0000.00.00",
            wineType = "Loading",
            region = "Region",
            star = 4,
            vintage = null,
            color = "#FF0000",
            buyAgain = true,
            public = true,
            varietal = "varietal",
            officialAlcohol = null,
            price = 0,
            smellKeywordList = listOf("-"),
            myWineTaste = MyWineTaste(
                sweetness = 0,
                acidity = 0,
                tannin = 0,
                body = 0,
                alcohol = 0,
                finish = 0
            ),
            defaultWineTaste = WineTaste(
                sweetness = 0,
                acidity = 0,
                tannin = 0,
                body = 0,
            ),
            memo = "-",
            tastingNoteImage = listOf(TastingNoteImage("1", "", Uri.EMPTY)),
        )
    }
}

data class WineTaste(
    val sweetness: Int,
    val acidity: Int,
    val tannin: Int,
    val body: Int,
)

data class MyWineTaste(
    val sweetness: Int,
    val acidity: Int,
    val tannin: Int,
    val body: Int,
    val alcohol: Int,
    val finish: Int
)

data class TastingNoteImage(
    val imgId: String,
    val imgUrl: String,
    val contentUri: Uri
)