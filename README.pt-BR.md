# on_audio_edit
<!-- https://img.shields.io/badge/Platform-Android%20%7C%20IOS-9cf?&style=flat-square -->
[![Pub.dev](https://img.shields.io/pub/v/on_audio_edit?color=9cf&label=Pub.dev&style=flat-square)](https://pub.dev/packages/on_audio_edit)
[![Platform](https://img.shields.io/badge/Platform-Android-9cf?logo=android&style=flat-square)](https://www.android.com/)
[![Flutter](https://img.shields.io/badge/Language-Flutter%20%7C%20Null--Safety-9cf?logo=flutter&style=flat-square)](https://www.flutter.dev/)
[![Kotlin](https://img.shields.io/badge/Language-Kotlin-9cf?logo=kotlin&style=flat-square)](https://kotlinlang.org/)

`on_audio_edit` é um [Flutter](https://flutter.dev/) Plugin usado para editar e ler audios/songs 🎶 informações/tags [Mp3, OggVorbis, Wav, etc...]. <br>

Esse Plugin usa [AdrienPoupa:jaudiotagger](https://github.com/AdrienPoupa/jaudiotagger) como dependência para editar audios tags.

## Ajuda:

**Algum problema? [Issues](https://github.com/LucasPJS/on_audio_edit/issues)** <br>
**Alguma sugestão? [Pull request](https://github.com/LucasPJS/on_audio_edit/pulls)**

### Traduções :

NOTE: Fique à vontade para ajudar nas traduções

* [Inglês](README.md)
* [Português](README.pt-BR.md)

## Tópicos:

<!-- * [Gif Examples](#gif-examples) -->
* [Como instalar](#como-instalar)
* [Como usar](#como-usar)
* [TagsType](#tagstype)
* [Exemplos](#exemplos)
* [Licença](#licença)

## Como instalar:
Adicione o seguinte codigo para seu `pubspec.yaml`:
```yaml
dependencies:
  on_audio_edit: ^1.1.0
```

#### Solicitar Permissões:
Se você quer usar a solicitação de permissões interna, irá precisar adicionar os seguintes codigos para seu `AndroidManifest.xml` <br>
**Note: Esse Plugin não tem um sistema de permissão interno.**
```xml
<manifest> ...

  <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
  <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>

</manifest>
```

#### Legacy External Storage:
Se você está usando/quer usar `Android 10` irá precisar adicionar os seguintes codigos para seu `AndroidManifest.xml` <br>
```xml
<application> ...

  android:requestLegacyExternalStorage="true"

</application>
```

## Algumas qualidades:

* Ler Audios/Songs tags.
* Editar Audios/Songs tags.
* **Suporta o Android 10 e superior.**.

## Para fazer:

* Adicionar uma melhor performace para todo o plugin.
* Adicionar `[deleteArtwork]` para Android 10 e superior.
* Adicionar `[deleteArtworks]` para Android 10 e superior.
* Adicionar `[deleteAudio]` para Android 10 e superior.
* Arrumar erros.

## Como usar:

```dart
OnAudioEdit() // O comando principal para usar o plugin.
```
Todos os tipos de métodos nesse plugin:

|  Methods  |   Parameters   |   Return   |
|--------------|-----------------|-----------------|
| [`readAudio`](#readaudio) | `(String)` | `Map<dynamic, dynamic>` | <br>
| [`readAllAudio`](#readallaudio) | `(String)` | `Map<TagsType, dynamic>` | <br>
| [`readAudios`](#readaudios) | `(List<String>)` | `List<AudiosTagModel>` | <br>
| [`readSingleAudioTag`](#readsingleaudiotag) | `(String, TagsType)` | `String` | <br>
| [`readSpecificsAudioTags`](#readspecificsaudiotags) | `(String, List<TagsType>)` | `Map<dynamic, dynamic>` | <br>
| [`editAudio`](#editaudio) | `(String, Map<TagsType, dynamic>)` | `bool` | <br>
| [`editAudios`](#editaudios) | `(List<String>, List<Map<TagsType, dynamic>>)` | `bool` | <br>
| [`editArtwork`](#editartwork) | `(String, bool, String, ArtworkFormat, int, String)` | `bool` | <br>
| [`deleteArtwork`]() | **[W]**`(String)` | `bool` | <br>
| [`deleteArtworks`]() | **[W]**`(List<String>)` | `bool` | <br>
| [`deleteAudio`]() | **[W]**`(String)` | `bool` | <br>
| [`getImagePath`]() |  | `String` | <br>
| [`permissionsStatus`]() |  | `bool` | <br>
| [`resetComplexPermission`]() | **[Q]** | `bool` | <br>
| [`requestComplexPermission`]() | **[Q]** | `bool` | <br>
| [`requestComplexPermission`]() | **[Q]** | `bool` | <br>

**[Q]** -> Apenas necessário no Android 10 ou superior.
**[W]** -> Esses métodos estão atualmente apenas implementados no Android 9 ou inferior.

## TagsType:

|  Types  |  Types  |  Types  |  Types  |  Types  |
|--------------|--------------|--------------|--------------|--------------|  
| `ALBUM_ARTIST` | `ORIGINAL_ARTIST` | `ORIGINAL_ALBUM` | `TRACK` | `FORMAT` | <br>
| `ARTIST` | `ORIGINAL_LYRICIST` | `LYRICS` | `TITLE` | `SAMPLE_RATE` | <br>
| `ARTISTS` | `ORIGINAL_YEAR` | `LANGUAGE` | `TEMPO` | `CHANNELS` | <br>
| `BEATS_PER_MINUTE` | `PRODUCER` | `KEY` | `TAGS` | `COVER_ART` | <br>
| `COMPOSER` | `QUALITY` | `ISRC` | `SUBTITLE` | `TYPE` | <br>
| `COUNTRY` | `RATING` | `FIRST_ARTWORK` | `LENGTH` |
| `GENRE` | `RECORD_LABEL` | `YEAR` | `BITRATE` | <br>

**Usando [readAllAudio](#readallaudio) você pode ver mais informações sobre o/os audio/audios. Veja toda a lista em: [AllTags](https://github.com/LucasPJS/on_audio_edit/blob/main/lib/details/types/tag_type.dart)**

## Exemplos:

#### readAudio
```dart
  // data: "/storage/1E65-6GH3/SomeMusic.mp3" or "/storage/someFolder/SomeMusic.mp3"
  Map<dynamic, dynamic> song = await OnAudioEdit().readAudio(data);
  var songTitle = song["TITLE"];
  var songArtist = song["ARTIST"];
```

#### readAllAudio
Esse método ler toda informação possível de um audio. Veja toda a lista em: [AllTags](https://github.com/LucasPJS/on_audio_edit/blob/main/lib/details/types/tag_type.dart)
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
⚠ **Note: Esse método não está funcionando no Android 10 ou superior. Use: [editAudio](#editaudio)**
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
⚠ **Note: Se openFilePicker é falso, imagePath não pode ser nulo.**
```dart
  // Parameters: openFilePicker, imagePath, format, size, description
  // DEFAULT: true, null, ArtworkFormat.JPEG, 24, "artwork"
  bool song = await OnAudioEdit().editArtwork(data);
  print(song); //True or False
```

## LICENÇA:

* [LICENÇA  ](https://github.com/LucasPJS/on_audio_edit/blob/main/LICENSE)

> * [Voltar ao Topo](#on_audio_edit)

