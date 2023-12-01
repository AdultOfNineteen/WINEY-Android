package com.teamwiney.ui.components.imagepicker

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

internal class ImageViewModel(
    private val repository: ImageRepository
) : ViewModel() {
    private val _images = mutableStateListOf<ImageInfo>()
    val images = _images

    private val _selectedImages = mutableStateListOf<ImageInfo>()
    val selectedImages = _selectedImages

    fun loadImages() {
        _images.clear()
        _images.addAll(repository.getImages())
    }

    fun insertImage(): Uri? {
        return repository.insertImage()
    }

    fun deleteImage(cameraUri: Uri?) {
        repository.deleteImage(cameraUri)
    }

    fun selectImage(image: ImageInfo) {
        _selectedImages.add(image)
    }

    fun removeImage(image: ImageInfo) {
        _selectedImages.remove(image)
    }

    fun clearImages() {
        _selectedImages.clear()
    }
}