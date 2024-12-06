package bob.colbaskin.hackatontemplate.auth.data

import bob.colbaskin.hackatontemplate.auth.data.models.LoginRequest
import bob.colbaskin.hackatontemplate.auth.data.models.RegisterRequest
import bob.colbaskin.hackatontemplate.auth.domain.local.TokenDataStoreRepository
import bob.colbaskin.hackatontemplate.auth.domain.network.AuthRepository
import bob.colbaskin.hackatontemplate.auth.domain.network.AuthService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val tokenDataStoreRepository: TokenDataStoreRepository
): AuthRepository {

    override suspend fun register(
        email: String,
        password: String,
        redirectUrl: String,
        clientId: Int,
        codeVerifier: String,
        codeChallengeMethod: String,
        scopes: List<String>,
        roleId: Int
    ) {
        val request = RegisterRequest(
            email = email,
            password = password,
            redirectUrl = redirectUrl,
            clientId = clientId,
            codeVerifier = codeVerifier,
            codeChallengeMethod = codeChallengeMethod,
            scopes = scopes,
            roleId = roleId
        )
        authService.register(request)
    }

    override suspend fun login(
        email: String,
        password: String,
        redirectUrl: String,
        codeVerifier: String,
        codeChallengeMethod: String,
        clientId: Int,
        scopes: List<String>
    ) {
        val request = LoginRequest(
            email = email,
            password = password,
            redirectUrl = redirectUrl,
            codeVerifier = codeVerifier,
            codeChallengeMethod = codeChallengeMethod,
            clientId = clientId,
            scopes = scopes
        )
        authService.login(request)
    }

    override fun isLoggedIn(): Boolean {
        return runBlocking {
            val token = tokenDataStoreRepository.getToken().first()
            !token.isNullOrEmpty()
        }
    }
}