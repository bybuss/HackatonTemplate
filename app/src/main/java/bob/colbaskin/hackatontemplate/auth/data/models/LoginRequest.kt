package bob.colbaskin.hackatontemplate.auth.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val email: String,
    val password: String,
    @SerialName("redirect_url") val redirectUrl: String,
    @SerialName("code_verifier") val codeVerifier: String,
    @SerialName("code_challenge_method") val codeChallengeMethod: String,
    @SerialName("client_id") val clientId: Int,
    val scopes: List<String>,
)
