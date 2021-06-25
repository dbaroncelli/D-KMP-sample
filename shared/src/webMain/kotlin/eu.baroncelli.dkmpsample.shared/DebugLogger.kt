package eu.baroncelli.dkmpsample.shared

actual class DebugLogger actual constructor(tagString : String) {
    actual val tag = tagString
    actual fun log(message: String) {
        console.log("$tag: $message")
    }
}