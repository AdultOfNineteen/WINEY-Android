package com.teamwiney.data.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.webkit.MimeTypeMap
import java.io.*
import java.math.BigInteger
import java.security.SecureRandom
import java.util.*

fun fileFromContentUri(context: Context, contentUri: Uri): File {
    val fileExtension = getFileExtension(context, contentUri)
    val fileName = "temp_file" + if (fileExtension != null) ".$fileExtension" else ""

    val tempFile = File(context.cacheDir, fileName)
    tempFile.createNewFile()

    try {
        val oStream = FileOutputStream(tempFile)
        val inputStream = context.contentResolver.openInputStream(contentUri)

        inputStream?.let {
            copy(inputStream, oStream)
        }

        oStream.flush()
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return tempFile
}

fun getFileExtension(context: Context, uri: Uri): String? {
    val fileType: String? = context.contentResolver.getType(uri)
    return MimeTypeMap.getSingleton().getExtensionFromMimeType(fileType)
}

@Throws(IOException::class)
private fun copy(source: InputStream, target: OutputStream) {
    val buf = ByteArray(8192)
    var length: Int
    while (source.read(buf).also { length = it } > 0) {
        target.write(buf, 0, length)
    }
}

private fun resizeImage(image: Bitmap): Bitmap {
    val width = image.width
    val height = image.height

    if (Integer.max(width, height) > 1280) {
        val newWidth: Int
        val newHeight: Int

        if (width > height) {
            newWidth = 1280
            newHeight = (newWidth * height) / width
        } else {
            newHeight = 1280
            newWidth = (newHeight * width) / height
        }

        return Bitmap.createScaledBitmap(image, newWidth, newHeight, true)
    } else {
        return image
    }
}

fun resizeAndSaveImage(context: Context, originalFile: File): File {
    try {
        val bitmap = BitmapFactory.decodeFile(originalFile.absolutePath)

        val exif = ExifInterface(originalFile.absolutePath)
        val rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)

        val matrix = Matrix()
        when (rotation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
            ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
            ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
        }

        val rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)

        val outputFile = createFile(context)
        outputFile.createNewFile()

        val outputStream = FileOutputStream(outputFile)

        rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
        outputStream.flush()
        outputStream.close()

        return outputFile
    } catch (e: IOException) {
        e.printStackTrace()
        return originalFile
    }
}

private fun createFile(context: Context): File {
    val fileName = generateRandomFileName(10) // 10자리의 임의 파일명 생성

    val storageDir = context.cacheDir

    return File(storageDir, "${fileName}.jpg")
}

private fun generateRandomFileName(length: Int): String {
    val random = SecureRandom()
    val randomBytes = ByteArray(length / 2)
    random.nextBytes(randomBytes)
    val bi = BigInteger(1, randomBytes)
    return String.format("%0${length}x", bi)
}