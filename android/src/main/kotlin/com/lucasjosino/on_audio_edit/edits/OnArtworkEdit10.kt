package com.lucasjosino.on_audio_edit.edits

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucasjosino.on_audio_edit.utils.checkArtworkFormat
import com.lucasjosino.on_audio_edit.utils.convertFileSize
import com.lucasjosino.on_audio_edit.utils.readBytes
import com.lucasjosino.on_audio_edit.utils.warningSizeCall
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jaudiotagger.audio.AudioFileIO
import org.jaudiotagger.audio.generic.Utils
import org.jaudiotagger.tag.TagOptionSingleton
import org.jaudiotagger.tag.images.ArtworkFactory
import org.jaudiotagger.tag.reference.PictureTypes
import java.io.*

@SuppressLint("StaticFieldLeak")
class OnArtworkEdit10(private val context: Context, private val activity: Activity) : ViewModel() {

    // Main parameters
    private val channelError = "on_audio_error"
    private val onSharedPrefKeyUriCode = "on_audio_edit_uri"
    private lateinit var uri: Uri
    private lateinit var call: MethodCall

    // Check if plugin already has uri.
    private fun getUri() : String? = activity.getSharedPreferences("on_audio_edit",
            Context.MODE_PRIVATE).getString(onSharedPrefKeyUriCode, "")

    //
    fun editArtwork(result: MethodChannel.Result, call: MethodCall, uri: Uri?) {
        // This will write in file removing all unnecessary info.
        TagOptionSingleton.getInstance().isId3v2PaddingWillShorten = true
        this.uri = uri ?: Uri.parse(call.argument<String>("imagePath")!!)
        this.call = call

        // Do everything in background to avoid bad performance.
        viewModelScope.launch {
            // Start editing
            val resultEditArtwork = doEverythingInBackground()

            // Flutter UI will start, but, information still loading
            result.success(resultEditArtwork)
        }
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    private suspend fun doEverythingInBackground() : Boolean = withContext(Dispatchers.IO) {
        // Get all information from Dart.
        val data = call.argument<String>("data")!!
        val type = checkArtworkFormat(call.argument<Int>("type")!!)
        val description = call.argument<String>("description")!!
        val size = call.argument<Int>("size")!!

        //
        val internalData = File(data)

        // Get and check if uri is null.
        val uriFolder = Uri.parse(getUri()) ?: return@withContext false

        // Use DocumentFile to navigate and find specific data inside specific folder.
        // We need this, cuz google blocked some access in Android >= 10/Q
        var pUri: Uri = Uri.parse("") // This can be null but, we use inside try / catch
        val dFile = DocumentFile.fromTreeUri(context, uriFolder)
        // [findFile] will give a slow performance, so, we use Kotlin Coroutines and "doEverythingInBackground"
        val fileList = dFile!!.findFile(internalData.name)
        if (fileList != null) pUri = fileList.uri

        // Temp file just to write(rewrite) file path. Produce the same result as "scan"
        val temp = File.createTempFile("tmp-media", '.' + Utils.getExtension(internalData))
        Utils.copy(internalData, temp)
        temp.deleteOnExit()

        // Setup audio edit
        val audioFile = AudioFileIO.read(File(data))
        val audioTag = audioFile.tag

        // Delete previous artwork
        audioTag.deleteArtworkField()

        // Getting image and creating artwork field
        val imageData = File(findImage(uri))
        val artwork = ArtworkFactory.createArtworkFromFile(imageData)

        // Setup Image
        artwork.pictureType = PictureTypes.DEFAULT_ID
        artwork.mimeType = type
        artwork.description = description
        artwork.height = size ; artwork.width = size

        //
        audioTag.setField(artwork)
        audioFile.file = temp

        try {
            AudioFileIO.write(audioFile)
        } catch (e: Exception) { Log.i(channelError, e.toString()) }

        // Start setup to write in folder
        // Until this moment we only write inside audio file, but we need tell android that this file has some change.
        val fis = FileInputStream(temp)
        val audioContent = readBytes(fis)

        // Warning
        // 3 Types: size >= 6, size >= 10 or size >= 13.
        // I won't block the write but will at least warning.
        // TODO Show a warning after user choose a image, if is bigger than 3 - 4 MB.
        val audioSize = convertFileSize(audioFile.file.length())
        warningSizeCall(audioSize, data)

        // Start writing in folder.
        try {
            context.contentResolver.openFileDescriptor(pUri, "rw")?.use { it ->
                FileOutputStream(it.fileDescriptor).use {
                    it.write(audioContent)
                    temp.delete()
                    return@withContext true
                }
            }
        } catch (e: Exception) {
            Log.i("on_audio_exception", e.toString())
            temp.delete()
            return@withContext false
        } catch (f: FileNotFoundException) {
            Log.i("on_audio_FileNotFound", f.toString())
            temp.delete()
            return@withContext false
        } catch (io: IOException) {
            Log.i("on_audio_IOException", io.toString())
            temp.delete()
            return@withContext false
        }

        // Delete temp folder.
        temp.delete()
        return@withContext false
    }

    @Suppress("DEPRECATION")
    //
    private fun findImage(uri: Uri) : String {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(uri, projection, null, null, null)

        //
        var imageData = ""
        if (cursor != null) {
            cursor.moveToFirst()
            imageData = cursor.getString(0)
        }
        cursor?.close()
        return imageData
    }
}