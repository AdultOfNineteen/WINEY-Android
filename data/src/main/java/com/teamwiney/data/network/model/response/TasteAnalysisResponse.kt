package com.teamwiney.data.network.model.response


data class TasteAnalysis(
    val avgPrice: Int = 0,
    val buyAgainCnt: Int = 0,
    val fortifiedCnt: Int = 0,
    val otherCnt: Int = 0,
    val recommendCountry: String = "",
    val recommendVarietal: String = "",
    val recommendWineType: String = "",
    val redCnt: Int = 0,
    val roseCnt: Int = 0,
    val sparklingCnt: Int = 0,
    val taste: Taste = Taste(),
    val top3Country: List<Top3Country> = emptyList(),
    val top3Varietal: List<Top3Varietal> = emptyList(),
    val top7Smell: List<Top7Smell> = emptyList(),
    val totalWineCnt: Int = 0,
    val whiteCnt: Int = 0
)

data class Taste(
    val acidity: Int = 0,
    val alcohol: Int = 0,
    val body: Int = 0,
    val finish: Int = 0,
    val sweetness: Int = 0,
    val tannin: Int = 0
)

data class Top3Country(
    val country: String,
    val percent: Int
)

data class Top3Varietal(
    val percent: Int,
    val varietal: String
)

data class Top7Smell(
    val percent: Int,
    val smell: String
)