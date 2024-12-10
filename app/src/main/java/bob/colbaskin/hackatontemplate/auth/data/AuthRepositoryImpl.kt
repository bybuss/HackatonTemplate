package bob.colbaskin.hackatontemplate.auth.data

import bob.colbaskin.hackatontemplate.auth.data.models.CodeToTokenDTO
import bob.colbaskin.hackatontemplate.auth.domain.local.AuthDataStoreRepository
import bob.colbaskin.hackatontemplate.auth.domain.network.AuthApiService
import bob.colbaskin.hackatontemplate.auth.domain.network.AuthRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataStoreRepository: AuthDataStoreRepository,
    private val authApiService: AuthApiService
): AuthRepository {

    override fun isLoggedIn(): Boolean {
        return runBlocking {
            val token = authDataStoreRepository.getToken().first()
            !token.isNullOrEmpty()
        }
    }

    override suspend fun codeToToken(request: CodeToTokenDTO) {
        val tokens = authApiService.codeToToken(request)

        authDataStoreRepository.saveToken(tokens.accessToken)
        authDataStoreRepository.saveRefreshToken(tokens.refreshToken)
    }
}