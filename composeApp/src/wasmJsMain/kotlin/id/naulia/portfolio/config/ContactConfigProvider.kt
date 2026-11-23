package id.naulia.portfolio.config

import kotlin.JsFun
import kotlinx.serialization.json.Json

@JsFun("() => { const config = globalThis.__NAULIA_CONTACT_CONFIG__; return config ? JSON.stringify(config) : null; }")
private external fun readRuntimeContactConfig(): String?

private val configJson = Json { ignoreUnknownKeys = true }

actual fun loadContactConfig(): ContactConfig {
    val rawConfig = readRuntimeContactConfig() ?: return ContactConfig.Default
    return runCatching { configJson.decodeFromString<ContactConfig>(rawConfig) }
        .getOrDefault(ContactConfig.Default)
}
