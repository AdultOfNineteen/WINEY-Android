package com.teamwiney.data.network.model.response

import com.google.gson.annotations.SerializedName

data class TastingNoteExists(
    @SerializedName("tastingNoteExists")
    val tastingNoteExists: Boolean
)
