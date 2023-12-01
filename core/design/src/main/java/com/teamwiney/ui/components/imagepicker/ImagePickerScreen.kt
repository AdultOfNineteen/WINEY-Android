package com.teamwiney.ui.components.imagepicker

import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.teamwiney.core.design.R
import com.teamwiney.ui.components.TopBar
import com.teamwiney.ui.components.WButton
import com.teamwiney.ui.theme.WineyTheme

@Composable
fun ImagePickerScreen(
    modifier: Modifier = Modifier,
    maxImgCount: Int = 3,
    onClosed: () -> Unit,
    onSelected: (List<Uri>) -> Unit
) {
    val context = LocalContext.current
    val viewModel: ImageViewModel = viewModel(
        factory = ImageViewModelFactory(
            repository = ImageRepository(context = context)
        )
    )

    BackHandler {
        onClosed()
    }

    LaunchedEffect(true) {
        viewModel.loadImages()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WineyTheme.colors.background_1)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        TopBar(
            content = "최근 항목"
        ) {
            viewModel.clearImages()
            onClosed()
        }
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            PickerContent(
                modifier = modifier.fillMaxSize(),
                loadImages = viewModel::loadImages,
                insertImage = viewModel::insertImage,
                deleteImage = viewModel::deleteImage,
                images = viewModel.images,
                selectedImages = viewModel.selectedImages,
                selectImage = viewModel::selectImage,
                removeImage = viewModel::removeImage,
                maxImgCount = maxImgCount
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
                    .background(color = Color.Transparent),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(Modifier.height(40.dp))

                WButton(
                    text = "완료",
                    onClick = {
                        onSelected(viewModel.selectedImages.map { it.contentUri })
                        viewModel.clearImages()
                    }
                )
                Spacer(Modifier.height(50.dp))
            }
        }
    }
}

@Composable
internal fun PickerContent(
    modifier: Modifier = Modifier,
    images: List<ImageInfo>,
    selectedImages: List<ImageInfo>,
    loadImages: () -> Unit,
    insertImage: () -> Uri?,
    deleteImage: (Uri?) -> Unit,
    selectImage: (ImageInfo) -> Unit,
    removeImage: (ImageInfo) -> Unit,
    maxImgCount: Int,
) {
    var cameraUri: Uri? = null

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                cameraUri?.let {
                    loadImages()
                }
            } else {
                deleteImage(cameraUri)
            }
        }

    LazyVerticalGrid(
        modifier = modifier.fillMaxWidth(),
        columns = GridCells.Fixed(3)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(1.0f)
                    .clickable {
                        cameraUri = insertImage()
                        cameraLauncher.launch(cameraUri)
                    },
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_camera_baseline_30),
                        tint = Color.Unspecified,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "사진 찍기",
                        style = WineyTheme.typography.bodyM2.copy(
                            color = WineyTheme.colors.main_2
                        )
                    )
                }
            }
        }

        items(images) { image ->
            ImageItem(
                image = image,
                selectedImages = selectedImages,
                selectImage = selectImage,
                removeImage = removeImage,
                maxImgCount = maxImgCount
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
internal fun ImageItem(
    image: ImageInfo,
    selectedImages: List<ImageInfo>,
    selectImage: (ImageInfo) -> Unit,
    removeImage: (ImageInfo) -> Unit,
    maxImgCount: Int
) {
    val selected = selectedImages.any { it.id == image.id }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                onClick = {
                    if (selected) {
                        removeImage(image)
                    } else {
                        if (selectedImages.size < maxImgCount) {
                            selectImage(image)
                        }
                    }
                }
            ),
        contentAlignment = Alignment.TopEnd
    ) {
        GlideImage(
            model = image.contentUri,
            contentDescription = "IMG_URL",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .aspectRatio(1.0F)
                .border(
                    width = 1.dp,
                    color = if (selected) WineyTheme.colors.main_1 else Color.Transparent
                )
        )

        if (selected) {
            ImageIndicator(imgCount = selectedImages.indexOf(image) + 1)
        }
    }
}


@Composable
internal fun ImageIndicator(
    imgCount: Int
) {
    Surface(
        modifier = Modifier
            .padding(10.dp)
            .size(24.dp),
        shape = CircleShape,
        color = WineyTheme.colors.main_1
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = "$imgCount",
                color = Color.White,
                fontSize = 14.sp,
            )
        }
    }
}

private fun checkAndRequestPermissions(
    context: Context,
    permissions: Array<String>,
    launcher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>,
) {
    val allPermissionsGranted = permissions.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

    if (!allPermissionsGranted) {
        launcher.launch(permissions)
    }
}