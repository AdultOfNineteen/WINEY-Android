package com.teamwiney.data.util

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

/** MultipartBody으로 바꾸는 작업 */
fun String.toPlainRequestBody() = requireNotNull(this).toRequestBody("text/plain".toMediaType())