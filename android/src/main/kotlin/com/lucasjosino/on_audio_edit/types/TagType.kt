package com.lucasjosino.on_audio_edit.types

import org.jaudiotagger.tag.FieldKey

fun checkTag(tag: Int) : FieldKey? {
    return when (tag) {
        0 -> FieldKey.ALBUM_ARTIST
        1 -> FieldKey.ARTIST
        2 -> FieldKey.ARTISTS
        3 -> FieldKey.BPM
//            FieldKey.BITRATE, 4
//            FieldKey.CHANNELS, 5
        6 -> FieldKey.COMPOSER
        7 -> FieldKey.COUNTRY
        8 -> FieldKey.COVER_ART
//            FieldKey.FIRST_ARTWORK, 9
//            FieldKey.FORMAT, 10
        11 -> FieldKey.GENRE
        12 -> FieldKey.ISRC
        13 -> FieldKey.KEY
        14 -> FieldKey.LANGUAGE
//            FieldKey.LENGTH, 15
        16 -> FieldKey.LYRICS
        17 -> FieldKey.ORIGINAL_ALBUM
        18 -> FieldKey.ORIGINAL_ARTIST
        19 -> FieldKey.ORIGINAL_LYRICIST
        20 -> FieldKey.ORIGINAL_YEAR
        21 -> FieldKey.PRODUCER
        22 -> FieldKey.QUALITY
        23-> FieldKey.RATING
        24 -> FieldKey.RECORD_LABEL
//            FieldKey.SAMPLE_RATE, 25
        26 -> FieldKey.SUBTITLE
        27 -> FieldKey.TAGS
        28 -> FieldKey.TEMPO
        29 -> FieldKey.TITLE
        30 -> FieldKey.TRACK
//            FieldKey.TYPE, 31
        32 -> FieldKey.YEAR
        else -> null
    }
}