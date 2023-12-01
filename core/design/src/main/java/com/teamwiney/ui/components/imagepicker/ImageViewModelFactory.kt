package com.teamwiney.ui.components.imagepicker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

internal class ImageViewModelFactory(
    private val repository: ImageRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ImageViewModel::class.java)) {
            ImageViewModel(repository) as T
        } else {
            throw IllegalArgumentException("ViewModel is Missing")
        }
    }
}