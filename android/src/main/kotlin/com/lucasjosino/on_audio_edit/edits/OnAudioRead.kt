package com.lucasjosino.on_audio_edit.edits

import com.lucasjosino.on_audio_edit.types.checkTag
import com.lucasjosino.on_audio_edit.utils.getAllProjection
import com.lucasjosino.on_audio_edit.utils.getProjection
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import org.jaudiotagger.audio.AudioFileIO
import org.jaudiotagger.audio.mp3.MP3File
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

        // ID Tag
        val mp3File = MP3File(data)
        if (mp3File.hasID3v1Tag()) tagsData["ID"] = "ID3v1Tag" else tagsData["ID"] = "ID3v2Tag"

        // Length
        tagsData["LENGTH"] = audioFile.file.length().toString()

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

            // ID Tag
            val mp3File = MP3File(pathData)
            if (mp3File.hasID3v1Tag()) tagsData["ID"] = "ID3v1Tag" else tagsData["ID"] = "ID3v2Tag"

            // Length
            tagsData["LENGTH"] = audioFile.file.length().toString()

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
        val mp3File = MP3File(data)
        val resultTag = when (tag) {
            7 -> if (mp3File.hasID3v1Tag()) "ID3v1Tag" else "ID3v2Tag"
            26 -> audioFile.file.length().toString()
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
            getTags.add(checkTag(it))
        }

        // Setup
        val audioData = File(data)
        val audioFile = AudioFileIO.read(audioData)
        val audioTag = audioFile.tag

        //
        val tagsData: MutableMap<String, Any> = HashMap()
        for (tag in getTags) tagsData[tag.name] = audioTag.getValue(tag, 0).orEmpty()

        if (tags.contains(7)) {
            // ID Tag
            val mp3File = MP3File(data)
            if (mp3File.hasID3v1Tag()) tagsData["ID"] = "ID3v1Tag"
            else tagsData["ID"] = "ID3v2Tag"
        }

        if (tags.contains(26)) {
            // Length
            tagsData["LENGTH"] = audioFile.file.length().toString()
        }

        // Sending to Dart
        result.success(tagsData)
    }
}