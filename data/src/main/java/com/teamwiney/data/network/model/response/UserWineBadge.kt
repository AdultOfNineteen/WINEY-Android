package com.teamwiney.data.network.model.response

import com.google.gson.annotations.SerializedName
import com.teamwiney.core.common.model.BadgeType

data class UserWineBadge(
    @SerializedName("sommelierBadgeList")
    val sommelierBadgeList: List<WineBadge>,
    @SerializedName("activityBadgeList")
    val activityBadgeList: List<WineBadge>

)

data class WineBadge(
    @SerializedName("badgeId")
    val badgeId: Long,
    @SerializedName("badgeType")
    val badgeType: BadgeType,
    @SerializedName("name")
    val name: String,
    @SerializedName("acquisitionMethod")
    val acquisitionMethod: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("acquiredAt")
    val acquiredAt: String?,
    @SerializedName("isRead")
    val isRead: Boolean?,
    @SerializedName("badgeImage")
    val badgeImage: String
)

