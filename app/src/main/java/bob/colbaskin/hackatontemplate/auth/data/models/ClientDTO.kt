package bob.colbaskin.hackatontemplate.auth.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ClientDTO(
    val name: String,
    @SerialName("base_url") val baseUrl: String,
    @SerialName("allowed_redirect_urls") val allowedRedirectUrls: List<String>,
    val type: Int
)
