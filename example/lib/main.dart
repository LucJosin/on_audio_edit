/*
Author: Lucas Josino
Github: https://github.com/LucasPJS
Plugin: on_audio_edit
Homepage: https://github.com/LucasPJS/on_audio_edit
Copyright: © 2021, Lucas Josino. All rights reserved.
License: https://github.com/LucasPJS/on_audio_edit/blob/main/LICENSE
*/

import 'package:flutter/material.dart';
import 'package:on_audio_edit/on_audio_edit.dart';
import 'package:on_audio_query/on_audio_query.dart';
import 'package:on_toast_widget/on_toast_widget.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> with SingleTickerProviderStateMixin {
  //
  TextEditingController name = TextEditingController();
  TextEditingController artist = TextEditingController();

  //
  late AnimationController _controller =
      AnimationController(vsync: this, duration: Duration(seconds: 2));

  //
  List<SongModel> songList = [];
  bool? result;

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: Text("Songs"),
          elevation: 2,
        ),
        body: Stack(
          children: [
            FutureBuilder(
              future: OnAudioQuery().querySongs(SongSortType.DEFAULT,
                  OrderType.ASC_OR_SMALLER, UriType.EXTERNAL, true),
              builder: (context, AsyncSnapshot<List<SongModel>> item) {
                if (item.data != null) {
                  songList = item.data!;
                  return RefreshIndicator(
                    onRefresh: () async {
                      setState(() {});
                    },
                    child: ListView.builder(
                      physics: BouncingScrollPhysics(),
                      itemCount: songList.length,
                      itemBuilder: (context, index) {
                        return ListTile(
                            onTap: () => optionsDialog(context, index),
                            title: Text(songList[index].title),
                            subtitle: Text(songList[index].artist),
                            trailing: Icon(Icons.arrow_forward_rounded),
                            leading: QueryArtworkWidget(
                              id: songList[index].id,
                              type: ArtworkType.AUDIO,
                            ));
                      },
                    ),
                  );
                }
                return CircularProgressIndicator();
              },
            ),
            OnToastWidget(
              effectType: EffectType.SLIDE,
              slidePositionType: SlidePositionType.BOTTOM,
              controller: _controller,
              child: Container(
                color: result == true ? Colors.green : Colors.red,
                height: 60,
                width: double.infinity,
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Text(result.toString()),
                    SizedBox(height: 10),
                    Text(result == true
                        ? "Pull to refresh to see the magic happening"
                        : "Opps, something wrong happened")
                  ],
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }

  optionsDialog(BuildContext context, int index) {
    return showDialog(
      context: context,
      builder: (widget) {
        return AlertDialog(
          title: Text("Choose a option"),
          content: Container(
            height: 120,
            child: Column(
              children: [
                ListTile(
                  title: Text("Edit Audio"),
                  onTap: () {
                    Navigator.pop(context);
                    editAudioDialog(context, index);
                  },
                ),
                ListTile(
                  title: Text("Edit Artwork"),
                  onTap: () async {
                    Navigator.pop(context);
                    result =
                        await OnAudioEdit().editArtwork(songList[index].data);
                    setState(() {
                      _controller.forward();
                    });
                  },
                )
              ],
            ),
          ),
          actions: [
            MaterialButton(
              onPressed: () {
                Navigator.pop(context);
              },
              child: Text("Cancel"),
            )
          ],
        );
      },
    );
  }

  editAudioDialog(BuildContext context, int index) {
    return showDialog(
      context: context,
      builder: (widget) {
        return AlertDialog(
          title: Text("Change info"),
          content: Container(
            height: 120,
            child: Column(
              children: [
                TextFormField(
                  controller: name,
                ),
                SizedBox(
                  height: 20,
                ),
                TextField(
                  controller: artist,
                )
              ],
            ),
          ),
          actions: [
            MaterialButton(
              onPressed: () async {
                Map<TagsType, dynamic> tag = {
                  TagsType.TITLE: name.text,
                  TagsType.ARTIST: artist.text,
                };
                Navigator.pop(context);
                result =
                    await OnAudioEdit().editAudio(songList[index].data, tag);
                setState(() {
                  _controller.forward();
                });
              },
              child: Text("Create"),
            ),
            MaterialButton(
              onPressed: () {
                Navigator.pop(context);
              },
              child: Text("Cancel"),
            )
          ],
        );
      },
    );
  }
}
