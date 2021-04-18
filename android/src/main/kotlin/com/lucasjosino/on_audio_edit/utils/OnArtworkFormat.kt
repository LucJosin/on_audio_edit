package com.lucasjosino.on_audio_edit.utils

import org.jaudiotagger.tag.id3.valuepair.ImageFormats
import java.lang.Exception

fun checkArtworkFormat(value: Int) : String {
    return when (value) {
        0 -> ImageFormats.MIME_TYPE_JPG
        1 -> ImageFormats.MIME_TYPE_JPEG
        2 -> ImageFormats.MIME_TYPE_PNG
        else -> throw Exception("[ImageFormats] don't exist")
    }
}