package com.teamwiney.ui.components.imagepicker

import android.net.Uri
import java.util.Date

data class ImageInfo(
    val id: Long,
    val displayName: String,
    val dateTaken: Date,
    val contentUri: Uri
)
