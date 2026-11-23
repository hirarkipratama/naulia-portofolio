package id.naulia.portfolio.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Book(
    val id: String,
    val title: String,
    val publisher: Publisher,
    val category: BookCategory,
    val ageRange: AgeRange,
    val roles: List<EditorRole>,
    val coverAsset: String,
    val year: Int,
    val synopsis: String = "",
    val pageCount: Int? = null,
)
