package com.lucasjosino.on_audio_edit.methods.read

import android.util.Log
import com.lucasjosino.on_audio_edit.types.checkTag
import com.lucasjosino.on_audio_edit.utils.checkAndGetExtraInfo
import com.lucasjosino.on_audio_edit.utils.getAllProjection
import com.lucasjosino.on_audio_edit.utils.getExtraInfo
import com.lucasjosino.on_audio_edit.utils.getProjection
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import org.jaudiotagger.audio.AudioFileIO
import org.jaudiotagger.tag.FieldKey
import java.io.File

class OnAudioRead {

    //
    fun readAudio(result: MethodChannel.Result, call: MethodCall) {
        // Get all information from Dart.
        val data = call.argument<String>("data")!!

        // Setup
        val audioData = File(data)
        val audioFile = AudioFileIO.read(audioData)
        val audioTag = audioFile.tag

        // Getting all tags
        val tagsData: MutableMap<String, Any> = HashMap()
        for (tag in getProjection()) tagsData[tag.name] = audioTag.getValue(tag, 0).orEmpty()
        Log.i("CheckCoverArt", tagsData["COVER_ART"].toString())

        // Extra information
        tagsData.putAll(checkAndGetExtraInfo(audioFile))

        // Sending to Dart
        result.success(tagsData)
    }

    fun readAllAudio(result: MethodChannel.Result, call: MethodCall) {
        // Get all information from Dart.
        val data = call.argument<String>("data")!!

        // Setup
        val audioData = File(data)
        val audioFile = AudioFileIO.read(audioData)
        val audioTag = audioFile.tag

        // Getting all tags
        val tagsData: MutableMap<String, Any> = HashMap()
        for (tag in getAllProjection()) tagsData[tag.name] = audioTag.getValue(tag, 0).orEmpty()

        // Sending to Dart
        result.success(tagsData)
    }

    //
    fun readAudios(result: MethodChannel.Result, call: MethodCall) {
        // Get all information from Dart.
        val data: ArrayList<String> = call.argument("data")!!

        // Getting all path
        val tagsList: ArrayList<MutableMap<String, Any>> = ArrayList()

        // Looping until get the last path
        for (pathData in data) {
            // Setup
            val audioData = File(pathData)
            val audioFile = AudioFileIO.read(audioData)
            val audioTag = audioFile.tag

            // Getting all tags
            val tagsData: MutableMap<String, Any> = HashMap()
            for (tag in getProjection()) tagsData[tag.name] = audioTag.getValue(tag, 0).orEmpty()

            // Extra information
            tagsData.putAll(checkAndGetExtraInfo(audioFile))

            tagsList.add(tagsData)
        }
        // Sending to Dart
        result.success(tagsList)
    }

    //
    fun readSingleAudioTag(result: MethodChannel.Result, call: MethodCall) {
        // Get all information from Dart.
        val data = call.argument<String>("data")!!
        val tag = call.argument<Int>("tag")!!

        // Setup
        val audioData = File(data)
        val audioFile = AudioFileIO.read(audioData)
        val audioTag = audioFile.tag

        // Getting specific tag
        val resultTag = when (tag) {
            4 -> audioFile.audioHeader.bitRate
            5 -> audioFile.audioHeader.channels
            9 -> audioTag.firstArtwork.binaryData.toString()
            10 -> audioFile.audioHeader.format
            15 -> audioFile.audioHeader.trackLength.toString()
            25 -> audioFile.audioHeader.sampleRate
            31 -> audioFile.audioHeader.encodingType
            else -> audioTag.getValue(checkTag(tag), 0).orEmpty()
        }

        // Sending to Dart
        result.success(resultTag)
    }

    //
    fun readSpecificsAudioTags(result: MethodChannel.Result, call: MethodCall) {
        // Get all information from Dart.
        val data = call.argument<String>("data")!!
        val tags: ArrayList<Int> = call.argument("tags")!!

        // Converting int to FieldKey
        val getTags: ArrayList<FieldKey> = ArrayList()
        tags.forEach {
            if (checkTag(it) != null) getTags.add(checkTag(it)!!)
        }

        // Setup
        val audioData = File(data)
        val audioFile = AudioFileIO.read(audioData)
        val audioTag = audioFile.tag

        //
        val tagsData: MutableMap<String, Any> = HashMap()
        for (tag in getTags) tagsData[tag.name] = audioTag.getValue(tag, 0).orEmpty()

        // Adding extra info using the worst method :P
        tags.forEach {
            if (checkTag(it) == null) getExtraInfo(audioFile, it, audioTag)
        }

        // Sending to Dart
        result.success(tagsData)
    }
}