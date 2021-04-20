package com.lucasjosino.on_audio_edit.utils

import org.jaudiotagger.audio.AudioFile
import org.jaudiotagger.tag.Tag

fun checkAndGetExtraInfo(audioFile: AudioFile) : MutableMap<String, Any> {
    val extraInfo: MutableMap<String, Any> = HashMap()
    extraInfo["BITRATE"] = audioFile.audioHeader.bitRate
    extraInfo["FORMAT"] = audioFile.audioHeader.format
    extraInfo["SAMPLE_RATE"] = audioFile.audioHeader.sampleRate
    extraInfo["CHANNELS"] = audioFile.audioHeader.channels
    extraInfo["TYPE"] = audioFile.audioHeader.encodingType
    extraInfo["LENGTH"] = audioFile.file.length().toString()
    extraInfo["FIRST_ARTWORK"] = audioFile.tag.firstArtwork.binaryData.toString()
    return extraInfo
}

fun getExtraInfo(audioFile: AudioFile, value: Int, audioTag: Tag) : String {
    return when (value) {
        4 -> audioFile.audioHeader.bitRate
        5 -> audioFile.audioHeader.channels
        9 -> audioTag.firstArtwork.binaryData.toString()
        10 -> audioFile.audioHeader.format
        15 -> audioFile.audioHeader.trackLength.toString()
        25 -> audioFile.audioHeader.sampleRate
        31 -> audioFile.audioHeader.encodingType
        else -> throw Exception("[getExtraInfo] gave a value that don't exist!")
    }
}