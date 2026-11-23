package id.naulia.portfolio.data.model

enum class BookFilter(val label: String, val category: BookCategory?) {
    ALL("Semua", null),
    SAINS(BookCategory.SAINS.label, BookCategory.SAINS),
    BUKU_CERITA(BookCategory.BUKU_CERITA.label, BookCategory.BUKU_CERITA),
    ENSIKLOPEDIA(BookCategory.ENSIKLOPEDIA.label, BookCategory.ENSIKLOPEDIA),
}

fun List<Book>.filterByCategory(selectedFilter: BookFilter): List<Book> =
    selectedFilter.category?.let { category -> filter { it.category == category } } ?: this
