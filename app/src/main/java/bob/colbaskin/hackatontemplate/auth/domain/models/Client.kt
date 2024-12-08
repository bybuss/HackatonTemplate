package bob.colbaskin.hackatontemplate.auth.domain.models

data class Client(
    val clientId: Int,
    val name: String,
    val baseUrl: String,
    val allowedRedirectUrls: List<String>,
    val type: Int
)