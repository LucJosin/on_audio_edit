package com.lucasjosino.on_audio_edit.types

import org.jaudiotagger.tag.FieldKey
import org.jaudiotagger.tag.Tag

fun checkTag(tag: Int): FieldKey? {
    return when (tag) {
        0 -> FieldKey.ACOUSTID_FINGERPRINT // 0
        1 -> FieldKey.ACOUSTID_ID // 1
        2 -> FieldKey.ALBUM // 2
        3 -> FieldKey.ALBUM_ARTIST // 3
        4 -> FieldKey.ALBUM_ARTIST_SORT // 4
        5 -> FieldKey.ALBUM_SORT // 5
        6 -> FieldKey.AMAZON_ID // 6
        7 -> FieldKey.ARRANGER // 7
        8 -> FieldKey.ARTIST // 8
        9 -> FieldKey.ARTIST_SORT // 9
        10 -> FieldKey.ARTISTS // 10
        11 -> FieldKey.BARCODE // 11
        12 -> FieldKey.BPM // 12
        13 -> FieldKey.CATALOG_NO // 13
        14 -> FieldKey.COMMENT // 14
        15 -> FieldKey.COMPOSER // 15
        16 -> FieldKey.COMPOSER_SORT // 16
        17 -> FieldKey.CONDUCTOR // 17
        18 -> FieldKey.COUNTRY // 18
        19 -> FieldKey.COVER_ART // 19
        20 -> FieldKey.CUSTOM1 // 20
        21 -> FieldKey.CUSTOM2 // 21
        22 -> FieldKey.CUSTOM3 // 22
        23 -> FieldKey.CUSTOM4 // 23
        24 -> FieldKey.CUSTOM5 // 24
        25 -> FieldKey.DISC_NO // 25
        26 -> FieldKey.DISC_SUBTITLE // 26
        27 -> FieldKey.DISC_TOTAL // 27
        28 -> FieldKey.DJMIXER // 28
        29 -> FieldKey.ENCODER // 29
        30 -> FieldKey.ENGINEER // 30
        31 -> FieldKey.FBPM // 31
        32 -> FieldKey.GENRE // 32
        33 -> FieldKey.GROUPING // 33
        34 -> FieldKey.ISRC // 34
        35 -> FieldKey.IS_COMPILATION // 35
        36 -> FieldKey.KEY // 36
        37 -> FieldKey.LANGUAGE // 37
        38 -> FieldKey.LYRICIST // 38
        39 -> FieldKey.LYRICS // 39
        40 -> FieldKey.MEDIA // 40
        41 -> FieldKey.MIXER // 41
        42 -> FieldKey.MOOD // 42
        43 -> FieldKey.MUSICBRAINZ_ARTISTID // 43
        44 -> FieldKey.MUSICBRAINZ_DISC_ID // 44
        45 -> FieldKey.MUSICBRAINZ_ORIGINAL_RELEASE_ID // 45
        46 -> FieldKey.MUSICBRAINZ_RELEASEARTISTID // 46
        47 -> FieldKey.MUSICBRAINZ_RELEASEID // 47
        48 -> FieldKey.MUSICBRAINZ_RELEASE_COUNTRY // 48
        49 -> FieldKey.MUSICBRAINZ_RELEASE_GROUP_ID // 49
        50 -> FieldKey.MUSICBRAINZ_RELEASE_STATUS // 50
        51 -> FieldKey.MUSICBRAINZ_RELEASE_TRACK_ID // 51
        52 -> FieldKey.MUSICBRAINZ_RELEASE_TYPE // 52
        53 -> FieldKey.MUSICBRAINZ_TRACK_ID // 53
        54 -> FieldKey.MUSICBRAINZ_WORK_ID // 54
        55 -> FieldKey.MUSICIP_ID // 55
        56 -> FieldKey.OCCASION // 56
        57 -> FieldKey.ORIGINAL_ALBUM // 57
        58 -> FieldKey.ORIGINAL_ARTIST // 58
        59 -> FieldKey.ORIGINAL_LYRICIST // 59
        60 -> FieldKey.ORIGINAL_YEAR // 60
        61 -> FieldKey.QUALITY // 61
        62 -> FieldKey.PRODUCER // 62
        63 -> FieldKey.RATING // 63
        64 -> FieldKey.RECORD_LABEL // 64
        65 -> FieldKey.REMIXER // 65
        66 -> FieldKey.SCRIPT // 66
        67 -> FieldKey.SUBTITLE // 67
        68 -> FieldKey.TAGS // 68
        69 -> FieldKey.TEMPO // 69
        70 -> FieldKey.TITLE // 70
        71 -> FieldKey.TITLE_SORT // 71
        72 -> FieldKey.TRACK // 72
        73 -> FieldKey.TRACK_TOTAL // 73
        74 -> FieldKey.URL_DISCOGS_ARTIST_SITE // 74
        75 -> FieldKey.URL_DISCOGS_RELEASE_SITE // 75
        76 -> FieldKey.URL_LYRICS_SITE // 76
        77 -> FieldKey.URL_OFFICIAL_ARTIST_SITE // 77
        78 -> FieldKey.URL_OFFICIAL_RELEASE_SITE // 78
        79 -> FieldKey.URL_WIKIPEDIA_ARTIST_SITE // 79
        80 -> FieldKey.URL_WIKIPEDIA_RELEASE_SITE // 80
        81 -> FieldKey.YEAR // 81
        else -> null
    }
}

fun Tag.getValueOrNull(key: FieldKey, n: Int): String? {
    return if (this.hasField(key)) this.getValue(key, n) else null
}