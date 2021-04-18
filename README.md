# on_audio_edit
<!-- https://img.shields.io/badge/Platform-Android%20%7C%20IOS-9cf?&style=flat-square -->
[![Pub.dev](https://img.shields.io/pub/v/on_audio_edit?color=9cf&label=Pub.dev&style=flat-square)](https://pub.dev/packages/on_audio_edit)
[![Platform](https://img.shields.io/badge/Platform-Android-9cf?logo=android&style=flat-square)](https://www.android.com/)
[![Flutter](https://img.shields.io/badge/Language-Flutter%20%7C%20Null--Safety-9cf?logo=flutter&style=flat-square)](https://www.flutter.dev/)
[![Kotlin](https://img.shields.io/badge/Language-Kotlin-9cf?logo=kotlin&style=flat-square)](https://kotlinlang.org/)

`on_audio_edit` is a [Flutter](https://flutter.dev/) Plugin used to edit and read audios/songs 🎶 infos/tags [Mp3, OggVorbis, Wav, etc...]. <br>

This Plugin use [AdrienPoupa:jaudiotagger](https://github.com/AdrienPoupa/jaudiotagger) as dependency to edit audios tags.

## Help:

**Any problem? [Issues](https://github.com/LucasPJS/on_audio_edit/issues)** <br>
**Any suggestion? [Pull request](https://github.com/LucasPJS/on_audio_edit/pulls)**

### Translations:

NOTE: Feel free to help with readme translations

* [English](README.md)
* [Portuguese](README.pt-BR.md)

### Topics:

<!-- * [Gif Examples](#gif-examples) -->
* [How to Install](#how-to-install)
* [How to use](#how-to-use)
* [TagsType](#tagstype)
* [Examples](#examples)
* [License](#license)

## How to Install:
Add the following code to your `pubspec.yaml`:
```yaml
dependencies:
  on_audio_edit: ^1.0.0
```

#### Request Permission:
You will need add the following code to your `AndroidManifest.xml` <br>
**Note: This Plugin don't have a built-in request permission**
```xml
<manifest> ...

  <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
  <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>

</manifest>
```

#### Legacy External Storage:
If you are using/want to use `Android 10` will need add the following code to your `AndroidManifest.xml` <br>
```xml
<application> ...

  android:requestLegacyExternalStorage="true"

</application>
```

## Some Features:

* Read Audios/Songs tags.
* Edit Audios/Songs tags.
* **Supports Android 10 and above**.

## TODO:

* Add better performance for all plugin.
* Fix bugs.

## How to use:

```dart
OnAudioEdit() // The main method to start using the plugin.
```
All types of methods on this plugin:

|  Methods  |   Parameters   |   Return   |
|--------------|-----------------|-----------------|
| [`readAudio`](#readaudio) | `(String)` | `Map<dynamic, dynamic>` | <br>
| [`readAllAudio`](#readallaudios) | `(String)` | `Map<TagsType, dynamic>` | <br>
| [`readAudios`](#readaudios) | `(List<String>)` | `List<AudiosTagModel>` | <br>
| [`readSingleAudioTag`](#readsingleaudiotag) | `(String, TagsType)` | `String` | <br>
| [`readSpecificsAudioTags`](#readspecificsaudiotags) | `(String, List<TagsType>)` | `Map<dynamic, dynamic>` | <br>
| [`editAudio`](#editaudio) | `(String, Map<TagsType, dynamic>)` | `bool` | <br>
| [`editAudios`](#editaudios) | `(List<String>, List<Map<TagsType, dynamic>>)` | `bool` | <br>
| [`editArtwork`](#editartwork) | `(String, bool, String, ArtworkFormat, int, String)` | `bool` | <br>
| [`getImagePath`]() |  | `String` | <br>
| [`permissionsStatus`]() |  | `bool` | <br>
| [`resetComplexPermission`]() | **[Q]** | `bool` | <br>
| [`requestComplexPermission`]() | **[Q]** | `bool` | <br>
| [`requestComplexPermission`]() | **[Q]** | `bool` | <br>

**[Q]** -> Only necessary on Android 10 or above.

## TagsType:

|  Types  |  Types  |  Types  |  Types  |
|--------------|--------------|--------------|--------------| 
| `ALBUM_ARTIST` | `ORIGINAL_ARTIST` | `ORIGINAL_ALBUM` | `TRACK` | <br>
| `ARTIST` | `ORIGINAL_LYRICIST` | `LYRICS` | `TITLE` | <br>
| `ARTISTS` | `ORIGINAL_YEAR` | `LANGUAGE` | `TEMPO` | <br>
| `BEATS_PER_MINUTE` | `PRODUCER` | `KEY` | `TAGS` | <br>
| `COMPOSER` | `QUALITY` | `ISRC` | `SUBTITLE` | <br>
| `COUNTRY` | `RATING` | `ID` | `LENGTH` | <br>
| `GENRE` | `RECORD_LABEL` | `YEAR` | <br>

## Examples:

#### readAudio
```dart
  // data: "/storage/1E65-6GH3/SomeMusic.mp3" or "/storage/someFolder/SomeMusic.mp3"
  Map<dynamic, dynamic> song = await OnAudioEdit().readAudio(data);
  var songTitle = song["TITLE"];
  var songArtist = song["ARTIST"];
```

#### readAllAudio
This method read all possible info from a audio. See all list in: [AllTags](#lib/details/types/tag_type.dart)
```dart
  Map<dynamic, dynamic> song = await OnAudioEdit().readAllAudio(data);
  var songInfo1 = song["MIXER"];
  var songInfo2 = song["MUSICBRAINZ_ORIGINAL_RELEASE_ID"];
  var songInfo3 = song["AMAZON_ID"];
  var songInfo4 = song["CONDUCTOR"];
```

#### readAudios
```dart
  List<String> allData = [data0, data1, data2];
  List<AudiosTagModel> song = await OnAudioEdit().readAudios(allData);
  ...
  var songTitle1 = song[1].title;
  var songTitle2 = song[2].title;
  var songTitle3 = song[3].title;
```

#### readSingleAudioTag
```dart
  String title = await OnAudioEdit().readSingleAudioTag(data, TagsType.TITLE);
  print(title); // Ex: Heavy, California
  ...
  String artist = await OnAudioEdit().readSingleAudioTag(data, TagsType.ARTIST);
  print(artist); // Ex: Jungle
```

#### readSpecificsAudioTags
```dart
  List<TagsType> tags = [
    TagsType.TITLE,
    TagsType.ARTIST
  ];
  var songSpecifics = await OnAudioEdit().readSpecificsAudioTags(data, tags);
  ...
  var songTitle = songSpecifics["TITLE"];
  var songArtist = songSpecifics["ARTIST"];
```

#### editAudio
```dart
  Map<TagsType, dynamic> tags = {
    TagsType.TITLE: "New Title",
    TagsType.ARTIST: "New Artist"
  };
  bool song = await OnAudioEdit().editAudio(data, tags);
  print(song); //True or False
```

#### editAudios
⚠ **Note: This method isn't implemented on Android 10 or above. Instead use: [editAudio](#editaudio)**
```dart
  // Tags
  List<<Map<TagsType, dynamic>> tags = [];
  Map<TagsType, dynamic> getTags = {
    TagsType.TITLE: "New Title",
    TagsType.ARTIST: "New Artist"
  };
  tags.add(getTags);

  // Songs data
  List<String> data;
  data.add(song1);
  data.add(song2);
  data.add(song3);
  bool result = await OnAudioEdit().editAudios(data, tags);
  print(result); //True or False
```

#### editAudio
⚠ **Note: If openFilePicker is false, imagePath can't be null.**
```dart
  // Parameters: openFilePicker, imagePath, format, size, description
  // DEFAULT: true, null, ArtworkFormat.JPEG, 24, "artwork"
  bool song = await OnAudioEdit().editArtwork(data);
  print(song); //True or False
```

## LICENSE:

* [LICENSE](https://github.com/LucasPJS/on_audio_edit/blob/main/LICENSE)

> * [Back to top](#on_audio_edit)

