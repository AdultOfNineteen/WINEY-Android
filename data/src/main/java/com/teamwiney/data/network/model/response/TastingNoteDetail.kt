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
)

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

//{
//    @Schema(name = "noteId")
//    private Long noteId;
//
//    @Schema(description = "작성 날짜")
//    private String noteDate;
//
//    @Schema(description = "와인 타입 RED, WHITE 등등")
//    private WineType wineType;
//
//    @Schema(description = "와인 이름")
//    private String wineName;
//
//    @Schema(description = "와인 생상지")
//    private String region;
//
//    @Schema(description = "별점")
//    private int star;
//
//    @Schema(description = "사용자가 지정한 색")
//    private String color;
//
//    @Schema(description = "재구매 의사")
//    private boolean buyAgain;
//
//    @Schema(description = "varietal")
//    private String varietal;
//
//    @Schema(description = "사용자가 입력한 알코올 도수")
//    private Double officialAlcohol;
//
//    @Schema(description = "사용자가 입력한 가격")
//    private int price;
//
//    private List<String> smellKeywordList;
//
//    @Schema(description = "내가 느낀 와인의 맛")
//    private MyWineTaste myWineTaste;
//
//    @Schema(description = "와인의 기본맛")
//    private DefaultWineTaste defaultWineTaste;
//
//    private List<TastingNoteImage> tastingNoteImage;
//
//    private String memo;
//}