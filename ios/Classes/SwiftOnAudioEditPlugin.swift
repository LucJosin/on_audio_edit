import Flutter
import UIKit

public class SwiftOnAudioEditPlugin: NSObject, FlutterPlugin {
    public static func register(with registrar: FlutterPluginRegistrar) {
        let channel = FlutterMethodChannel(name: "com.lucasjosino.on_audio_edit", binaryMessenger: registrar.messenger())
        let instance = SwiftOnAudioEditPlugin()
        registrar.addMethodCallDelegate(instance, channel: channel)
    }

    public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
        switch call.method {
        case "readAudios":
            OnReadAudio(call: call, result: result).readAudios()
        default:
            result(FlutterMethodNotImplemented)
        }
    }
}
