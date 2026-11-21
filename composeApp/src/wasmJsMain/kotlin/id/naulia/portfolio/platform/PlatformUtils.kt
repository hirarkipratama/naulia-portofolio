package id.naulia.portfolio.platform

import kotlin.JsFun
import kotlinx.browser.window

@JsFun("(value) => encodeURIComponent(value)")
private external fun encodeUriComponent(value: String): String

@JsFun("() => window.matchMedia('(prefers-reduced-motion: reduce)').matches")
private external fun matchesReducedMotionPreference(): Boolean

actual fun openWhatsApp(number: String, message: String) {
    openUrl("https://wa.me/$number?text=${encodeUriComponent(message)}")
}

actual fun openEmail(email: String) {
    openUrl("mailto:$email")
}

actual fun openUrl(url: String) {
    window.open(url, "_blank")
}

actual fun prefersReducedMotion(): Boolean = matchesReducedMotionPreference()
