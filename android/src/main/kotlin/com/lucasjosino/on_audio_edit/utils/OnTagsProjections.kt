package com.lucasjosino.on_audio_edit.utils

import org.jaudiotagger.tag.FieldKey

fun getAllProjection() : List<FieldKey> = allProjection

private var allProjection = listOf(
        FieldKey.ACOUSTID_FINGERPRINT, // 0
        FieldKey.ACOUSTID_ID, // 1
        FieldKey.ALBUM, // 2
        FieldKey.ALBUM_ARTIST, // 3
        FieldKey.ALBUM_ARTIST_SORT, // 4
        FieldKey.ALBUM_SORT, // 5
        FieldKey.AMAZON_ID, // 6
        FieldKey.ARRANGER, // 7
        FieldKey.ARTIST, // 8
        FieldKey.ARTIST_SORT, // 9
        FieldKey.ARTISTS, // 10
        FieldKey.BARCODE, // 11
        FieldKey.BPM, // 12
        FieldKey.CATALOG_NO, // 13
        FieldKey.COMMENT, // 14
        FieldKey.COMPOSER, // 15
        FieldKey.COMPOSER_SORT, // 16
        FieldKey.CONDUCTOR, // 17
        FieldKey.COUNTRY, // 18
        FieldKey.COVER_ART, // 19
        FieldKey.CUSTOM1, // 20
        FieldKey.CUSTOM2, // 21
        FieldKey.CUSTOM3, // 22
        FieldKey.CUSTOM4, // 23
        FieldKey.CUSTOM5, // 24
        FieldKey.DISC_NO, // 25
        FieldKey.DISC_SUBTITLE, // 26
        FieldKey.DISC_TOTAL, // 27
        FieldKey.DJMIXER, // 28
        FieldKey.ENCODER, // 29
        FieldKey.ENGINEER, // 30
        FieldKey.FBPM, // 31
        FieldKey.GENRE, // 32
        FieldKey.GROUPING, // 33
        FieldKey.ISRC, // 34
        FieldKey.IS_COMPILATION, // 35
        FieldKey.KEY, // 36
        FieldKey.LANGUAGE, // 37
        FieldKey.LYRICIST, // 38
        FieldKey.LYRICS, // 39
        FieldKey.MEDIA, // 40
        FieldKey.MIXER, // 41
        FieldKey.MOOD, // 42
        FieldKey.MUSICBRAINZ_ARTISTID, // 43
        FieldKey.MUSICBRAINZ_DISC_ID, // 44
        FieldKey.MUSICBRAINZ_ORIGINAL_RELEASE_ID, // 45
        FieldKey.MUSICBRAINZ_RELEASEARTISTID, // 46
        FieldKey.MUSICBRAINZ_RELEASEID, // 47
        FieldKey.MUSICBRAINZ_RELEASE_COUNTRY, // 48
        FieldKey.MUSICBRAINZ_RELEASE_GROUP_ID, // 49
        FieldKey.MUSICBRAINZ_RELEASE_STATUS, // 50
        FieldKey.MUSICBRAINZ_RELEASE_TRACK_ID, // 51
        FieldKey.MUSICBRAINZ_RELEASE_TYPE, // 52
        FieldKey.MUSICBRAINZ_TRACK_ID, // 53
        FieldKey.MUSICBRAINZ_WORK_ID, // 54
        FieldKey.MUSICIP_ID, // 55
        FieldKey.OCCASION, // 56
        FieldKey.ORIGINAL_ALBUM, // 57
        FieldKey.ORIGINAL_ARTIST, // 58
        FieldKey.ORIGINAL_LYRICIST, // 59
        FieldKey.ORIGINAL_YEAR, // 60
        FieldKey.QUALITY, // 61
        FieldKey.PRODUCER, // 62
        FieldKey.RATING, // 63
        FieldKey.RECORD_LABEL, // 64
        FieldKey.REMIXER, // 65
        FieldKey.SCRIPT, // 66
        FieldKey.SUBTITLE, // 67
        FieldKey.TAGS, // 68
        FieldKey.TEMPO, // 69
        FieldKey.TITLE, // 70
        FieldKey.TITLE_SORT, // 71
        FieldKey.TRACK, // 72
        FieldKey.TRACK_TOTAL, // 73
        FieldKey.URL_DISCOGS_ARTIST_SITE, // 74
        FieldKey.URL_DISCOGS_RELEASE_SITE, // 75
        FieldKey.URL_LYRICS_SITE, // 76
        FieldKey.URL_OFFICIAL_ARTIST_SITE, // 77
        FieldKey.URL_OFFICIAL_RELEASE_SITE, // 78
        FieldKey.URL_WIKIPEDIA_ARTIST_SITE, // 79
        FieldKey.URL_WIKIPEDIA_RELEASE_SITE, // c
        FieldKey.YEAR // 81
)