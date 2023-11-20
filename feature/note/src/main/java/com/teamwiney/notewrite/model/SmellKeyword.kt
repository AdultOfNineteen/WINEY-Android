package com.teamwiney.notewrite.model

enum class SmellKeyword(val type: String, val enType: String, val krType: String) {
    FRUIT("과일향", "FRUIT", "과일향"),
    BERRY("과일향", "BERRY", "베리류"),
    LEMONANDLIME("과일향", "LEMONANDLIME", "레몬/라임"),
    APPLEPEAR("과일향", "APPLEPEAR", "사과/배"),
    PEACHPLUM("과일향", "PEACHPLUM", "복숭아/자두"),
    TROPICALFRUIT("과일향", "TROPICALFRUIT", "열대과일"),
    FLOWER("내추럴", "FLOWER", "꽃향"),
    GRASSWOOD("내추럴", "GRASSWOOD", "풀/나무"),
    HERB("내추럴", "HERB", "허브향"),
    OAK("오크향", "OAK", "오크향"),
    SPICE("오크향", "SPICE", "향신료"),
    NUTS("오크향", "NUTS", "견과류"),
    VANILLA("오크향", "VANILLA", "바닐라"),
    CHOCOLATE("오크향", "CHOCOLATE", "초콜릿"),
    FLINT("기타", "FLINT", "부싯돌"),
    BREAD("기타", "BREAD", "빵"),
    RUBBER("기타", "RUBBER", "고무"),
    EARTHASH("기타", "EARTASH", "흙/재"),
    MEDICINE("기타", "MEDICNE", "약품");

    companion object {
        fun find(name: String): String? {
            return SmellKeyword.values().find { it.type == name }?.enType
        }
    }
}
