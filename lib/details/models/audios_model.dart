part of on_audio_edit;

/// [AudiosTagModel] contains all Songs Tags.
class AudiosTagModel {
  AudiosTagModel(this._info);

  /// The type dynamic is used for both but, the map is always based in [String, dynamic]
  Map<dynamic, dynamic> _info;

  /// Return song [albumArtist]
  String get albumArtist => _info["ALBUM_ARTIST"];

  /// Return song [artist]
  String get artist => _info["ARTIST"];

  /// Return song [artists]
  String get artists => _info["ARTISTS"];

  /// Return song [beatsPerMinutes]
  String get beatsPerMinutes => _info["BEATS_PER_MINUTE"];

  /// Return song [composer]
  String get composer => _info["COMPOSER"];

  /// Return song [country]
  String get country => _info["COUNTRY"];

  /// Return song [genre]
  String get genre => _info["GENRE"];

  /// Return song [id]
  String get id => _info["ID"];

  /// Return song [isrc]
  String get isrc => _info["ISRC"];

  /// Return song [key]
  String get key => _info["KEY"];

  /// Return song [language]
  String get language => _info["LANGUAGE"];

  /// Return song [lyrics]
  String get lyrics => _info["LYRICS"];

  /// Return song [originalAlbum]
  String get originalAlbum => _info["ORIGINAL_ALBUM"];

  /// Return song [originalAlbum]
  String get originalArtist => _info["ORIGINAL_ARTIST"];

  /// Return song [originalLyricist]
  String get originalLyricist => _info["ORIGINAL_LYRICIST"];

  /// Return song [originalYear]
  String get originalYear => _info["ORIGINAL_YEAR"];

  /// Return song [producer]
  String get producer => _info["PRODUCER"];

  /// Return song [quality]
  String get quality => _info["QUALITY"];

  /// Return song [rating]
  String get rating => _info["RATING"];

  /// Return song [recordLabel]
  String get recordLabel => _info["RECORD_LABEL"];

  /// Return song [subTitle]
  String get subTitle => _info["SUBTITLE"];

  /// Return song [tags]
  String get tags => _info["TAGS"];

  /// Return song [tempo]
  String get tempo => _info["TEMPO"];

  /// Return song [title]
  String get title => _info["TITLE"];

  /// Return song [track]
  String get track => _info["TRACK"];

  /// Return song [year]
  String get year => _info["YEAR"];
}
