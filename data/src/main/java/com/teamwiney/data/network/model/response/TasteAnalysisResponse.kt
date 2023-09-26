package com.teamwiney.data.network.model.response


data class TasteAnalysis(
    val avgPrice: Int = 0,
    val recommendCountry: String = "",
    val recommendVarietal: String = "",
    val recommendWineType: String = "",
    val taste: Taste = Taste(),
    val top3Type: List<Top3Type> = emptyList(),
    val top3Country: List<Top3Country> = emptyList(),
    val top3Varietal: List<Top3Varietal> = emptyList(),
    val top7Smell: List<Top7Smell> = emptyList(),
    val totalWineCnt: Int = 0,
    val buyAgainCnt: Int = 0,
)

data class Taste(
    val acidity: Double = 0.0,
    val alcohol: Double = 0.0,
    val body: Double = 0.0,
    val finish: Double = 0.0,
    val sweetness: Double = 0.0,
    val tannin: Double = 0.0
)

data class Top3Type(
    val type: String,
    val percent: Int
)

data class Top3Country(
    val country: String,
    val count: Int
)

data class Top3Varietal(
    val varietal: String,
    val percent: Int
)

data class Top7Smell(
    val smell: String,
    val percent: Int
)