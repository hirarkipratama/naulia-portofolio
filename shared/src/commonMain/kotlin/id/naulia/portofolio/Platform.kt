package id.naulia.portofolio

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform