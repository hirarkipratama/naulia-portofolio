package id.naulia.portfolio.ui.theme

import id.naulia.portfolio.data.model.BookCategory
import id.naulia.portfolio.ui.components.PillTone

fun BookCategory.badgeTone(): PillTone = when (this) {
    BookCategory.SAINS -> PillTone.Tertiary
    BookCategory.BUKU_CERITA -> PillTone.Primary
    BookCategory.ENSIKLOPEDIA -> PillTone.Spotlight
}
