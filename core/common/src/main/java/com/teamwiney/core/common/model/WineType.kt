package com.teamwiney.core.common.model


/** 서버에서 사용하는 와인타입 Enum Class */
enum class WineType(val type: String, val korType: String) {
    RED("RED", "레드"),
    WHITE("WHITE", "화이트"),
    SPARKLING("SPARKLING", "스파클링"),
    ROSE("ROSE", "로제"),
    FORTIFIED("FORTIFIED", "포르투"),
    OTHER("OTHER", "기타");

    companion object {

        fun convertToNoteType(type: String): String {
            return when (type) {
                "SPARKLING" -> "SPARKL"
                "FORTIFIED" -> "PORT"
                "OTHER" -> "ETC"
                else -> type
            }
        }

        fun typeOf(type: String): WineType {
            return when (type) {
                "RED" -> RED
                "WHITE" -> WHITE
                "SPARKLING" -> SPARKLING
                "ROSE" -> ROSE
                "FORTIFIED" -> FORTIFIED
                else -> OTHER
            }
        }
    }
}