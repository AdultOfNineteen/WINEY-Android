package com.teamwiney.data.network.model.response

import com.google.gson.annotations.SerializedName

data class DeleteUser(
    @SerializedName("userId") val userId: Int,
    @SerializedName("deletedAt") val deletedAt: String
)
