package id.naulia.portfolio.data.model

import kotlinx.serialization.Serializable

@Serializable
enum class AgeRange(val displayName: String) {
    AGE_3_6("Usia 3–6"),
    AGE_4_8("Usia 4–8"),
    AGE_7_12("Usia 7–12"),
    ALL_AGES("Semua Usia"),
}
