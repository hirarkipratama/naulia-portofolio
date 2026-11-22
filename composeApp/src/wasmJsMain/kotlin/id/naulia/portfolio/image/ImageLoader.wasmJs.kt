package id.naulia.portfolio.image

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asComposeImageBitmap
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.js.Promise
import kotlinx.coroutines.await
import org.jetbrains.skia.Bitmap
import org.jetbrains.skia.Image

@kotlin.JsFun("""
(url) => new Promise((resolve, reject) => {
    fetch(url)
        .then(r => {
            if (!r.ok) throw new Error(r.statusText);
            return r.arrayBuffer();
        })
        .then(buffer => {
            let binary = '';
            const bytes = new Uint8Array(buffer);
            const len = bytes.byteLength;
            const chunkSize = 8192;
            for (let i = 0; i < len; i += chunkSize) {
                binary += String.fromCharCode.apply(null, bytes.subarray(i, Math.min(i + chunkSize, len)));
            }
            resolve(btoa(binary));
        })
        .catch(err => reject(err));
})
""")
private external fun fetchBase64(url: String): Promise<JsString>

private val memoryCache = mutableMapOf<String, ImageBitmap>()

@OptIn(ExperimentalEncodingApi::class)
@Composable
actual fun rememberImageBitmap(url: String): ImageBitmap? {
    if (url.isBlank()) return null
    var bitmapState by remember(url) { mutableStateOf(memoryCache[url]) }

    LaunchedEffect(url) {
        if (bitmapState == null) {
            val cached = memoryCache[url]
            if (cached != null) {
                bitmapState = cached
            } else {
                try {
                    val base64Str = fetchBase64(url).await<JsString>().toString()
                    val bytes = Base64.decode(base64Str)
                    val skiaImage = Image.makeFromEncoded(bytes)
                    val skiaBitmap = Bitmap.makeFromImage(skiaImage)
                    val composeBitmap = skiaBitmap.asComposeImageBitmap()
                    memoryCache[url] = composeBitmap
                    bitmapState = composeBitmap
                } catch (t: Throwable) {
                    // Fall back to placeholder
                }
            }
        }
    }

    return bitmapState
}
