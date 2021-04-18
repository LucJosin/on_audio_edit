/*
Author: Lucas Josino
Github: https://github.com/LucasPJS
Plugin: on_audio_edit
Homepage: https://github.com/LucasPJS/on_audio_edit
Copyright: © 2021, Lucas Josino. All rights reserved.
License: https://github.com/LucasPJS/on_audio_edit/blob/main/LICENSE
*/

package com.lucasjosino.on_audio_edit

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.lucasjosino.on_audio_edit.edits.*

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry

/** OnAudioEditPlugin */
class OnAudioEditPlugin: FlutterPlugin, MethodCallHandler, ActivityAware, PluginRegistry.ActivityResultListener {

  // Dart <-> Kotlin communication
  private val channelName = "com.lucasjosino.on_audio_edit"
  private val channelError = "on_audio_error"
  private lateinit var channel : MethodChannel

  // Main parameters
  private var externalRequest: Boolean = false
  private lateinit var activity: Activity
  private lateinit var context: Context
  private lateinit var result: Result
  private lateinit var call: MethodCall

  //
  private val onPermission = arrayOf(
          Manifest.permission.READ_EXTERNAL_STORAGE,
          Manifest.permission.WRITE_EXTERNAL_STORAGE
  )

  // This is only important for initialization
  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    this.context = flutterPluginBinding.applicationContext
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, channelName)
    channel.setMethodCallHandler(this)
  }

  // Main method to communication between Dart <-> Kotlin
  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    this.result = result ; this.call = call
    // Check Basics Permissions
    if (checkSimplePermissions()) {
      when (call.method) {
        // Permissions methods
        "permissionsStatus" -> if (checkSimplePermissions()) result.success(true) else result.success(false)

        // Complex permission (Android 10)
        "resetComplexPermission" -> resetComplexPermission()
        "complexPermissionStatus" -> if (isSdCardGranted()) result.success(true) else result.success(false)
        "requestComplexPermission" -> if (Build.VERSION.SDK_INT >= 29) {
          openTree()
          // When calling request Complex Permission from external(Dart), cancel the editing file.
          // So, only will ask for permission, save it and return to Dart.
          this.externalRequest = true
        } else result.error(channelError, "[requestComplexPermission] it's only necessary on Android 10 or above", null)

        // Read methods
        "readAudio" -> OnAudioRead().readAudio(result, call)
        "readAllAudio" -> OnAudioRead().readAllAudio(result, call)
        "readAudios" -> OnAudioRead().readAudios(result, call)
        "readSingleAudioTag" -> OnAudioRead().readSingleAudioTag(result, call)
        "readSpecificsAudioTags" -> OnAudioRead().readSpecificsAudioTags(result, call)

        // Write methods
        "editAudio" -> if (Build.VERSION.SDK_INT < 29) OnAudioEdit(context).editAudio(result, call)
          else editAudioController()
        "editAudios" -> if (Build.VERSION.SDK_INT >= 29)
          result.error(channelError, "Unfortunately this method isn't implemented on Android 10 and above, " +
                  "If you wanna help: https://github.com/LucasPJS/on_audio_edit", null)
          else OnAudioEdit(context).editAudios(result, call)

        // Write artwork methods
        "editArtwork" -> if (call.argument<Boolean>("openFilePicker")!!) getImageForArtwork(0) else {
          if (Build.VERSION.SDK_INT >= 29)
            OnArtworkEdit10(context, activity).editArtwork(result, call, null)
          else OnArtworkEdit(context).editArtwork(result, call, null)
        }

        // Image Picker
        "getImagePath" -> getImageForArtwork(1)

        // Method don't exist
        else -> result.notImplemented()
      }
    } else result.error(channelError, "[on_audio_edit] needs [READ] and [WRITE] permissions to work.", null)
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }

  // This is only important for initialization - Start
  override fun onAttachedToActivity(binding: ActivityPluginBinding) {
    this.activity = binding.activity
    binding.addActivityResultListener(this)
  }

  override fun onDetachedFromActivityForConfigChanges() {}

  override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {}

  override fun onDetachedFromActivity() {}
  // End

  // Check basics permissions
  // Return true if both read and write permissions are granted or false if are't.
  private fun checkSimplePermissions(): Boolean = onPermission.all {
    return ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
  }

  // Artwork picker

  //
  private val onImagePickerInternalCode = 88561
  private val onImagePickerExternalCode = 88562

  // Open the image folder to user select.
  private fun getImageForArtwork(IorE: Int) {
    val code = if (IorE == 0) onImagePickerInternalCode else onImagePickerExternalCode
    val intent = Intent(Intent.ACTION_PICK)
    intent.type = "image/*"
    activity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), code)
  }

  // Check complex permission for Android 29/Q/10

  // Main parameters
  private val onAudioEditCode = 8856
  private val onSharedPrefKeyRequestCode = "on_audio_edit_requestPermission"
  private val onSharedPrefKeyUriCode = "on_audio_edit_uri"
  private lateinit var uri: Uri

  // Check if plugin already has permission.
  private fun isSdCardGranted() : Boolean = activity.getSharedPreferences("on_audio_edit",
          Context.MODE_PRIVATE).getBoolean(onSharedPrefKeyRequestCode, false)

  private fun editAudioController() {
    if (Build.VERSION.SDK_INT > 21) {
      if (isSdCardGranted()) OnAudioEdit10(context, activity).editAudio10(result, call) else openTree()
    }
  }

  // Open the tree(folder screen) to user select.
  // [SHOW_ADVANCED] will show to user more specific description about what we need.
  @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
  private fun openTree() {
    val advancedIntent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
    advancedIntent.putExtra("android.content.extra.SHOW_ADVANCED", true)
    activity.startActivityForResult(advancedIntent, onAudioEditCode)
  }

  // Get persistent permission to avoid multiples request and save it using shared pref.
  // Probably will add options for more folders. Cuz, user can only select one folder with this method.
  // > [can only select one folder] if folder has subfolder document file still providing permission.
  @RequiresApi(Build.VERSION_CODES.KITKAT)
  private fun onSaveTree() {
    val intentRead = Intent.FLAG_GRANT_READ_URI_PERMISSION
    val intentWrite = Intent.FLAG_GRANT_WRITE_URI_PERMISSION
    context.contentResolver.takePersistableUriPermission(uri, intentRead or intentWrite)
    //Save for next time
    val sharedPref = activity.getSharedPreferences("on_audio_edit", Context.MODE_PRIVATE)
    sharedPref.edit().putBoolean(onSharedPrefKeyRequestCode, true).apply()
    sharedPref.edit().putString(onSharedPrefKeyUriCode, uri.toString()).apply()
  }

  // Reset shared prefs with uri.
  private fun resetComplexPermission() {
    val sharedPref = activity.getSharedPreferences("on_audio_edit", Context.MODE_PRIVATE)
    sharedPref.edit().remove(onSharedPrefKeyRequestCode).apply()
    sharedPref.edit().remove(onSharedPrefKeyUriCode).apply()
    result.success(true)
  }

  // OnEditPermissionController
  // It's ugly but functional:
  //
  // Important:
  //  * This is only necessary on Android >= 10/Q, cuz, google has changed storage permission.
  //
  // #onAudioEditCode
  // * Check if [requestCode] or [resultData] is null, if true, throw a error and back to dart, else
  // call [onSaveTree] that will save uri and avoid multiples folders select from the user. After all this,
  // check if the folder request is from internal or external, if it's from external, only save thee and back to dart,
  // else, go to edit audio.
  //
  // #onImagePickerInternalCode
  // * Check if [requestCode] or [resultData] is null, if true, throw a error and back to dart, else
  // call edit artwork.
  //
  // #onImagePickerExternalCode
  // * Check if [requestCode] or [resultData] is null, if true, throw a error and back to dart, else
  // go back to Dart with image uri.
  //
  // If the requestCode is null, throw a error. Maaaybe, my fault :/
  @RequiresApi(Build.VERSION_CODES.KITKAT)
  override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?): Boolean {
    // Edit normal fields
    when (requestCode) {
      onAudioEditCode -> {
        if (resultCode == RESULT_OK && resultData != null) {
          resultData.data?.also { uri -> this.uri = uri }
          onSaveTree()
          if (!externalRequest) OnAudioEdit10(context, activity).editAudio10(result, call)
          return true
        } else result.error(channelError, "[resultCode] or [resultData] returned null.", null) ; return false
      }
      // Edit artwork field
      onImagePickerInternalCode -> {
        if (resultCode == RESULT_OK && resultData != null) {
          resultData.data?.also {
            if (Build.VERSION.SDK_INT >= 29)
              OnArtworkEdit10(context, activity).editArtwork(result, call, resultData.data!!)
              else OnArtworkEdit(context).editArtwork(result, call, resultData.data!!)
            return true
          }
        } else result.error(channelError, "[OIPIC] - [resultCode] or [resultData] returned null.", null) ; return false
      }
      onImagePickerExternalCode -> {
        if (resultCode == RESULT_OK && resultData != null) {
          result.success(resultData.data)
          return true
        } else result.error(channelError, "[OIPEC] - [resultCode] or [resultData] returned null.", null) ; return false
      }
      else -> result.error(channelError, "RequestCode: [$requestCode] don't exist, probably my fault, open a issue in GitHub", null)
    }
    return false
  }
}
