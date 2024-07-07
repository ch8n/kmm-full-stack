import org.jetbrains.skia.Bitmap
import org.jetbrains.skia.Image

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }

}