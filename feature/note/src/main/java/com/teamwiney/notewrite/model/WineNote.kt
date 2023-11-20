package com.teamwiney.notewrite.model

import android.net.Uri

data class WineNote(
    val wineId: Long,
    val vintage: String,
    val officialAlcohol: Double,
    val price: String,
    val color: String,
    val sweetness: Int,
    val acidity: Int,
    val alcohol: Int,
    val body: Int,
    val tannin: Int,
    val finish: Int,
    val memo: String,
    val buyAgain: Boolean?,
    val rating: Int,
    val imgs: List<Uri>,
    val smellKeywordList: List<String>
) {
    companion object {
        fun default(): WineNote {
            return WineNote(
                wineId = 0,
                vintage = "",
                officialAlcohol = 0.0,
                price = "",
                color = "",
                sweetness = 0,
                acidity = 0,
                alcohol = 12,
                body = 0,
                tannin = 0,
                finish = 0,
                memo = "",
                buyAgain = null,
                rating = 0,
                imgs = emptyList(),
                smellKeywordList = emptyList()
            )
        }
    }
}
