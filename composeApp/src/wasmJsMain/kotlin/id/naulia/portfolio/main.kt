package id.naulia.portfolio

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    val body = requireNotNull(document.body) { "Document body is required to host the Compose viewport" }
    ComposeViewport(body) {
        App()
    }
}
