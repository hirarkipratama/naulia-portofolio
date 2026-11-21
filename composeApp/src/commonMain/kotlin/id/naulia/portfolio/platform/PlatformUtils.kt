package id.naulia.portfolio.platform

expect fun openWhatsApp(number: String, message: String)

expect fun openEmail(email: String)

expect fun openUrl(url: String)

expect fun prefersReducedMotion(): Boolean
