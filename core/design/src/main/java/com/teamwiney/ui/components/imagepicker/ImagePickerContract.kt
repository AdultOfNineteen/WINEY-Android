package com.teamwiney.ui.components.imagepicker

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract

const val RESULT_URI = "resultUri"
const val MAX_IMAGE_COUNT = "maxImageCount"

class ImagePickerContract : ActivityResultContract<Int, ArrayList<Uri>?>() {
    override fun createIntent(context: Context, maxImageCount: Int): Intent {
        return Intent(context, ImagePickerActivity::class.java).apply {
            putExtra(MAX_IMAGE_COUNT, maxImageCount)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): ArrayList<Uri>? {
        if (resultCode == Activity.RESULT_OK) {
            val imagesUriStringList = intent?.getStringArrayListExtra(RESULT_URI)
            if (imagesUriStringList != null) {
                val uriList = ArrayList<Uri>()
                for (uriString in imagesUriStringList) {
                    val uri = Uri.parse(uriString)
                    uriList.add(uri)
                }
                return uriList
            }
        }
        return null
    }
}