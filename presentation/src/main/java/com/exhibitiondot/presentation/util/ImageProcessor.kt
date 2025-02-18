package com.exhibitiondot.presentation.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.graphics.Matrix
import androidx.exifinterface.media.ExifInterface
import android.net.Uri
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.io.path.createTempFile

@Singleton
class ImageProcessor @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    suspend fun uriToCompressedFile(uri: Uri, reqWidth: Int, reqHeight: Int): Result<File> = runCatching {
        val (suffix, compressFormat) = getSuffixAndCompressFormatByVersion()
        val tempFile = createTempImageFile(suffix = suffix)
        val downSampledBitmap = getDownSampledBitmap(uri, reqWidth, reqHeight).getOrThrow()
        val correctedBitmap = rotateBitmapIfRequired(downSampledBitmap, uri).getOrThrow()
        compressBitmapToFile(correctedBitmap, tempFile, compressFormat)
            .getOrThrow()
            .also {
                downSampledBitmap.recycle()
                correctedBitmap.recycle()
            }
        tempFile
    }

    private fun getSuffixAndCompressFormatByVersion(): Pair<String, CompressFormat> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            ".webp" to CompressFormat.WEBP_LOSSY
        } else {
            ".jpeg" to CompressFormat.JPEG
        }
    }

    // 1. 임시 파일 만들기 -> 전송 성공 이후 삭제할 것임.
    private fun createTempImageFile(suffix: String): File {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createTempFile(
                directory = context.cacheDir.toPath(),
                prefix = FILE_NAME_PREFIX,
                suffix = suffix
            ).toFile()
        } else {
            File.createTempFile(FILE_NAME_PREFIX, suffix, context.cacheDir)
        }
    }

    // 2. bitmap 다운샘플링 옵션 설정 후 uri -> bitmap 디코딩
    private fun getDownSampledBitmap(uri: Uri, reqWidth: Int, reqHeight: Int): Result<Bitmap> {
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
            decodeUriToBitmap(uri, this)
            inSampleSize = calcInSampleSize(reqWidth, reqHeight)
            inJustDecodeBounds = false
        }
        return decodeUriToBitmap(uri, options)
    }

    // 2-1) uri -> bitmap 으로 디코딩
    private fun decodeUriToBitmap(uri: Uri, options: BitmapFactory.Options): Result<Bitmap> = runCatching {
            context.contentResolver.openInputStream(uri).use { inputStream ->
                BitmapFactory.decodeStream(inputStream, null, options)
                    ?: throw RuntimeException("bitmap decoding failed")
            }
        }


    // 2-2) reqWidth, reqHeight 으로 inSampleSize 구하기
    private fun BitmapFactory.Options.calcInSampleSize(reqWidth: Int, reqHeight: Int): Int {
        val (width, height) = outWidth to outHeight
        var inSampleSize = 1
        if (width > reqWidth || height > reqHeight) {
            val halfWidth = width shr 1
            val halfHeight = height shr 1
            while (halfWidth / inSampleSize >= reqWidth && halfHeight / inSampleSize >= reqHeight) {
                inSampleSize = inSampleSize shl 1
            }
        }
        return inSampleSize
    }

    // 3. 디코딩 중 exif 회전 정보가 무시될 수 있음 -> 다시 원상 복구
    private suspend fun rotateBitmapIfRequired(bitmap: Bitmap, uri: Uri): Result<Bitmap> =
        withContext(Dispatchers.Default) {
            runCatching {
                context.contentResolver.openInputStream(uri)?.use { inputStream ->
                    val exif = ExifInterface(inputStream)
                    val orientation = exif.getAttributeInt(
                        ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_NORMAL
                    )
                    val degrees = when (orientation) {
                        ExifInterface.ORIENTATION_ROTATE_90 -> 90f
                        ExifInterface.ORIENTATION_ROTATE_180 -> 180f
                        ExifInterface.ORIENTATION_ROTATE_270 -> 270f
                        else -> 0f
                    }
                    if (degrees == 0f) {
                        bitmap
                    } else {
                        val matrix = Matrix().apply { postRotate(degrees) }
                        Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
                    }
                } ?: bitmap
            }
        }


    // 4. webp or jpeg 형식 파일 압축
    private fun compressBitmapToFile(
        bitmap: Bitmap,
        file: File,
        compressFormat: CompressFormat,
        quality: Int = 80,
    ) = runCatching {
        FileOutputStream(file).use { outStream ->
            bitmap.compress(compressFormat, quality, outStream)
        }
    }

    companion object {
        private const val FILE_NAME_PREFIX = "dot_upload_"
        private const val TAG = "이미지 프로세서"
    }
}