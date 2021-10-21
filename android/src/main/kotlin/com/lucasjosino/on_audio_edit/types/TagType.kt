package com.lucasjosino.on_audio_edit.types

import org.jaudiotagger.tag.FieldKey

fun checkTag(tag: Int) : FieldKey? {
    return when (tag) {
        0 -> FieldKey.ALBUM
        1 -> FieldKey.ALBUM_ARTIST
        2 -> FieldKey.ARTIST
        3 -> FieldKey.ARTISTS
        4 -> FieldKey.BPM
//            FieldKey.BITRATE, 5
//            FieldKey.CHANNELS, 6
        7 -> FieldKey.COMPOSER
        8 -> FieldKey.COUNTRY
        9 -> FieldKey.COVER_ART
//            FieldKey.FIRST_ARTWORK, 10
//            FieldKey.FORMAT, 11
        12 -> FieldKey.GENRE
        13 -> FieldKey.ISRC
        14 -> FieldKey.KEY
        15 -> FieldKey.LANGUAGE
//            FieldKey.LENGTH, 16
        17 -> FieldKey.LYRICS
        18 -> FieldKey.ORIGINAL_ALBUM
        19 -> FieldKey.ORIGINAL_ARTIST
        20 -> FieldKey.ORIGINAL_LYRICIST
        21 -> FieldKey.ORIGINAL_YEAR
        22 -> FieldKey.PRODUCER
        23 -> FieldKey.QUALITY
        24-> FieldKey.RATING
        25 -> FieldKey.RECORD_LABEL
//            FieldKey.SAMPLE_RATE, 26
        27 -> FieldKey.SUBTITLE
        28 -> FieldKey.TAGS
        29 -> FieldKey.TEMPO
        30 -> FieldKey.TITLE
        31 -> FieldKey.TRACK
//            FieldKey.TYPE, 32
        33 -> FieldKey.YEAR
        else -> null
    }
}