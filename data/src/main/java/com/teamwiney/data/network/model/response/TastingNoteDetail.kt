package com.teamwiney.data.network.model.response

data class TastingNoteDetail(
    val noteId: Long,
    val noteDate: String,
    val wineType: String,
    val wineName: String,
    val region: String,
    val star: Int,
    val color: String,
    val buyAgain: Boolean,
    val varietal: String,
    val officialAlcohol: Double,
    val price: Int,
    val smellKeywordList: List<String>,
    val myWineTaste: MyWineTaste,
    val defaultWineTaste: WineTaste,
    val tastingNoteImage: List<TastingNoteImage>,
    val memo: String,
) {
    companion object {
        fun default() = TastingNoteDetail(
            noteId = 0L,
            wineName = "-",
            noteDate = "0000.00.00",
            wineType = "Loading",
            region = "Region",
            star = 4,
            color = "RED",
            buyAgain = true,
            varietal = "varietal",
            officialAlcohol = 0.0,
            price = 0,
            smellKeywordList = listOf("-"),
            myWineTaste = MyWineTaste(
                sweetness = 0.0,
                acidity = 0.0,
                tannin = 0.0,
                body = 0.0,
                alcohol = 0.0,
                finish = 0.0
            ),
            defaultWineTaste = WineTaste(
                sweetness = 0.0,
                acidity = 0.0,
                tannin = 0.0,
                body = 0.0,
            ),
            memo = "-",
            tastingNoteImage = listOf(TastingNoteImage("1", "1")),
        )
    }

    fun getWine() = Wine(
        wineId = -1,
        type = this.wineType,
        name = this.wineName,
        country = this.region,
        varietal = this.varietal,
        sweetness = this.myWineTaste.sweetness.toInt(),
        acidity = this.myWineTaste.acidity.toInt(),
        body = this.myWineTaste.body.toInt(),
        tannins = this.myWineTaste.tannin.toInt(),
        wineSummary = WineSummary(
            avgPrice = this.price.toDouble(),
            avgSweetness = this.defaultWineTaste.sweetness.toInt(),
            avgAcidity = this.defaultWineTaste.acidity.toInt(),
            avgBody = this.defaultWineTaste.body.toInt(),
            avgTannins = this.defaultWineTaste.tannin.toInt()
        )
    )
}

data class WineTaste(
    val sweetness: Double,
    val acidity: Double,
    val tannin: Double,
    val body: Double,
)

data class MyWineTaste(
    val sweetness: Double,
    val acidity: Double,
    val tannin: Double,
    val body: Double,
    val alcohol: Double,
    val finish: Double
)

data class TastingNoteImage(
    val imgId: String,
    val imgUrl: String
)