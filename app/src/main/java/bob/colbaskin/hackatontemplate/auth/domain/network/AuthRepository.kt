package bob.colbaskin.hackatontemplate.auth.domain.network

interface AuthRepository {

    suspend fun register(
        email: String,
        password: String,
        redirectUrl: String,
        clientId: Int,
        codeVerifier: String,
        codeChallengeMethod: String,
        scopes: List<String>,
        roleId: Int
    )

    suspend fun login(
        email: String,
        password: String,
        redirectUrl: String,
        codeVerifier: String,
        codeChallengeMethod: String,
        clientId: Int,
        scopes: List<String>
    )

    fun isLoggedIn(): Boolean
}