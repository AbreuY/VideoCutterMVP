package com.example.myapplication

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.os.Environment
import android.text.TextUtils
import java.io.File
import java.util.concurrent.TimeUnit

@Suppress("MemberVisibilityCanBePrivate", "unused") // Not relevant for Utils class
object VideoFileUtils {

    fun getMediaCacheDirPath(context: Context): String {
        val dirPath = context.getExternalFilesDir(null)?.absolutePath + "/mediaCache"
        val file = File(dirPath)
        if (!file.exists()) {
            file.mkdir()
        }
        return dirPath
    }

    fun getPublicSpaceDirPath(context: Context):String {
        val dirPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        return dirPath.path
    }

    fun clearAllMediaCache(context: Context) {
        val mediaCacheDirPath = getMediaCacheDirPath(context)
        val dirFile = File(mediaCacheDirPath)
        if (dirFile.isDirectory) {
            val children = dirFile.list()
            for (i in children.indices) {
                File(dirFile, children[i]).delete()
            }
        }
    }

    fun extractMiddleFrame(file: File): Bitmap {
        val mediaMetadataRetriever = MediaMetadataRetriever()
        mediaMetadataRetriever.setDataSource(file.absolutePath)
        val videoDuration = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        val middleVideoFrameTime =
            videoDuration.toLong() / 2 //TODO -> Check with product which frame should be taken as a preview
        val middleFrameTimeMicroSec = TimeUnit.MILLISECONDS.toMicros(middleVideoFrameTime)
        return mediaMetadataRetriever.getFrameAtTime(middleFrameTimeMicroSec, MediaMetadataRetriever.OPTION_CLOSEST)
    }

    fun getBaseFileName(fileName: String): String? {
        return if (TextUtils.isEmpty(fileName) || !fileName.contains(".") || fileName.endsWith(".")) {
            null
        } else {
            fileName.substring(0, fileName.lastIndexOf("."))
        }
    }

    fun getFileExtension(fileName: String): String? {
        return if (TextUtils.isEmpty(fileName) || !fileName.contains(".") || fileName.endsWith(".")) null else fileName.substring(
            fileName.lastIndexOf(".") + 1
        )
    }
}