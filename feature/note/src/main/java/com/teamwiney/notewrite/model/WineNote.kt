package com.teamwiney.notewrite.model

import androidx.compose.ui.graphics.Color
import com.teamwiney.data.network.model.response.TastingNoteImage
import com.teamwiney.notewrite.WineSmellOption

data class WineNote(
    val wineId: Long,
    val vintage: String,
    val officialAlcohol: Double?,
    val price: String = "",
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
    val selectedImages: List<TastingNoteImage>,
    val loadImages: List<TastingNoteImage>,
    val addImages: List<TastingNoteImage>,
    val deleteImages: List<TastingNoteImage>,
    val smellKeywordList: List<WineSmellOption>,
    val loadSmellKeywordList: List<WineSmellOption>,
    val addSmellKeywordList: List<WineSmellOption>,
    val deleteSmellKeywordList: List<WineSmellOption>
) {
    companion object {
        fun default(): WineNote {
            return WineNote(
                wineId = 0,
                vintage = "",
                officialAlcohol = 12.0,
                price = "",
                color = Color(0xFF59002B),
                sweetness = 0,
                acidity = 0,
                alcohol = 0,
                body = 0,
                tannin = 0,
                finish = 0,
                memo = "",
                buyAgain = null,
                rating = 0,
                selectedImages = emptyList(),
                loadImages = emptyList(),
                addImages = emptyList(),
                deleteImages = emptyList(),
                smellKeywordList = emptyList(),
                loadSmellKeywordList = emptyList(),
                addSmellKeywordList = emptyList(),
                deleteSmellKeywordList = emptyList()
            )
        }
    }
}
