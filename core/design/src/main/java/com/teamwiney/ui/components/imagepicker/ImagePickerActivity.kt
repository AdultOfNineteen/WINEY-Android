package com.teamwiney.ui.components.imagepicker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat

class ImagePickerActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                setResult(Activity.RESULT_CANCELED)
                finish()
            }
        }
        onBackPressedDispatcher.addCallback(this, callback)

        val receivedIntent = intent
        val maxImageCount = receivedIntent.getIntExtra(MAX_IMAGE_COUNT, 3)

        setContent {
            ImagePickerScreen(
                maxImgCount = maxImageCount,
                onClosed = {
                   finish()
                },
                onSelected = { images ->
                    val uriString = images.map { it.toString() }

                    Intent().apply {
                        putStringArrayListExtra(RESULT_URI, ArrayList(uriString))
                        setResult(Activity.RESULT_OK, this)
                        finish()
                    }
                }
            )
        }
    }

}