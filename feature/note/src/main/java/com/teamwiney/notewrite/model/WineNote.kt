package com.teamwiney.notewrite.model

import android.net.Uri
import androidx.compose.ui.graphics.Color

data class WineNote(
    val wineId: Long,
    val vintage: String,
    val officialAlcohol: Double?,
    val price: String,
    val color: Color,
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
                officialAlcohol = 12.0,
                price = "",
                color = Color.Red,
                sweetness = 0,
                acidity = 0,
                alcohol = 0,
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
