package bob.colbaskin.hackatontemplate.auth.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val email: String,
    val password: String,
    @SerialName("redirect_url") val redirectUrl: String,
    @SerialName("client_id") val clientId: Int,
    @SerialName("code_verifier") val codeVerifier: String,
    @SerialName("code_challenge_method") val codeChallengeMethod: String,
    val scopes: List<String>,
    @SerialName("role_id") val roleId: Int
)
