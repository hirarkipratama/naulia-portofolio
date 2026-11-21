package id.naulia.portfolio.data.model

import kotlinx.serialization.Serializable

@Serializable
enum class Publisher(val displayName: String) {
    GRAMEDIA("Gramedia"),
    BIP("BIP"),
    MIZAN("Mizan"),
    ERLANGGA("Erlangga"),
    OTHER("Lainnya"),
}
