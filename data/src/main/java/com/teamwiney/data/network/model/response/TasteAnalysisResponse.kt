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

fun TasteAnalysis.toDomain() = TasteAnalysis(
    avgPrice = this.avgPrice,
    recommendCountry = this.recommendCountry,
    recommendVarietal = this.recommendVarietal,
    recommendWineType = convertTypeToSpecies(this.recommendWineType),
    taste = this.taste,
    top3Type = this.top3Type.map { top3Type ->
        Top3Type(convertTypeToColor(top3Type.type), top3Type.percent)
    },
    top3Country = this.top3Country,
    top3Varietal = this.top3Varietal,
    top7Smell = this.top7Smell,
    totalWineCnt = this.totalWineCnt,
    buyAgainCnt = this.buyAgainCnt
)

private fun convertTypeToSpecies(type: String): String {
    return when (type) {
        "RED" -> "레드 와인"
        "ROSE" -> "로제 와인"
        "WHITE" -> "화이트 와인"
        "SPARKLING" -> "스파클링 와인"
        "FORTIFIED" -> "포트 와인"
        "OTHER" -> "기타 와인"
        else -> "기타 와인"
    }
}

private fun convertTypeToColor(type: String): String {
    return when (type) {
        "RED" -> "레드"
        "ROSE" -> "로제"
        "WHITE" -> "화이트"
        "SPARKLING" -> "스파클링"
        "FORTIFIED" -> "포트"
        "OTHER" -> "기타"
        else -> "기타"
    }
}