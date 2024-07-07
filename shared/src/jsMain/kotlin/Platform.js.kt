import org.jetbrains.skia.Bitmap

class JsPlatform : Platform {
    override val name: String
        get() = "Js Target"
}

actual fun getPlatform(): Platform {
    return JsPlatform()
}