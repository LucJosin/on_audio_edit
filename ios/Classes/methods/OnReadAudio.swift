import Foundation
import MediaPlayer
import OSLog

public class OnReadAudio {
    let args: [String: Any]
    let result: FlutterResult

    init(call: FlutterMethodCall, result: @escaping FlutterResult) {
        // To make life easy, add all arguments inside a map.
        args = call.arguments as! [String: Any]
        self.result = result
    }

    func readAudios() {
        let data = args["data"] as! [String]

        DispatchQueue.global(qos: .userInitiated).async {
            var listOfSongs: [[String: Any?]] = Array()

            for uri in data {
                let url = URL(string: uri)!

                let file = try! AVAudioFile(forReading: url)
                let fileFormat = file.fileFormat
                let settings = fileFormat.settings

                //  bithDepth of the song, available only with lossless formats:
                // 'flac', 'wma', 'alac'
                let bitDepth = settings.first(where: { $0.key == AVEncoderBitDepthHintKey })?.value as? String
                let finalBitDepth: Int64? = bitDepth == nil ? nil : Int64(bitDepth!)

                // try to read the song real bitRate
                let bitRate = settings.first(where: { $0.key == AVEncoderBitRateKey })?.value as? String
                // if cannot read it, calculate it approximately by: length / sampleRate
                let finalBitRate: Int64? = bitRate == nil ? Int64(file.length) / Int64(fileFormat.sampleRate) : Int64(bitRate!)

                let info = self.toJson(
                    channels: Int64(fileFormat.channelCount),
                    sampleRate: Int64(fileFormat.sampleRate),
                    bitRate: finalBitRate,
                    bitDepth: finalBitDepth
                )
                listOfSongs.append(info)
            }

            // After finish the "query", go back to the "main" thread
            // (You can only call flutter inside the main thread).
            DispatchQueue.main.async {
                self.result(listOfSongs)
            }
        }
    }

    func toJson(channels: Int64?, sampleRate: Int64?, bitRate: Int64?, bitDepth: Int64?) -> [String: Any] {
        return [
            "CHANNELS": channels as Any,
            "SAMPLE_RATE": sampleRate as Any,
            "BITRATE": bitRate as Any,
            "BIT_DEPTH": bitDepth as Any,
        ]
    }
}
