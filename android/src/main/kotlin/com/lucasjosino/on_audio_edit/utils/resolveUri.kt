package com.lucasjosino.on_audio_edit.utils

import android.content.Context
import android.net.Uri
import android.provider.MediaStore

fun resolveFileUri(context: Context, uri: Uri): String {
    return if (uri.scheme == "content" && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
        val cursor = context.contentResolver.query(uri, arrayOf(MediaStore.Audio.AudioColumns.DATA), null, null)!!
        cursor.moveToFirst()
        val filePath = cursor.getString(0)
        cursor.close()
        filePath!!
    } else {
        uri.path!!
    }
}