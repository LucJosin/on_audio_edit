package com.lucasjosino.on_audio_edit.types

import org.jaudiotagger.tag.FieldKey

fun checkTag(tag: Int) : FieldKey {
    return when (tag) {
        0 -> FieldKey.ALBUM_ARTIST
        1 -> FieldKey.ARTIST
        2 -> FieldKey.ARTISTS
        3 -> FieldKey.BPM
        4 -> FieldKey.COMPOSER
        5 -> FieldKey.COUNTRY
        6 -> FieldKey.GENRE
        8 -> FieldKey.ISRC
        9 -> FieldKey.KEY
        10 -> FieldKey.LANGUAGE
        11 -> FieldKey.LYRICS
        12 -> FieldKey.ORIGINAL_ALBUM
        13 -> FieldKey.ORIGINAL_ARTIST
        14 -> FieldKey.ORIGINAL_LYRICIST
        15 -> FieldKey.ORIGINAL_YEAR
        16 -> FieldKey.PRODUCER
        17 -> FieldKey.QUALITY
        18 -> FieldKey.RATING
        19 -> FieldKey.RECORD_LABEL
        20 -> FieldKey.SUBTITLE
        21 -> FieldKey.TAGS
        22 -> FieldKey.TEMPO
        23 -> FieldKey.TITLE
        24 -> FieldKey.TRACK
        25 -> FieldKey.YEAR
        else -> throw Exception("Value passed to [checkTag] don't exist")
    }
}