package bob.colbaskin.hackatontemplate.auth.data

import android.util.Log
import bob.colbaskin.hackatontemplate.auth.data.models.ClientDTO
import bob.colbaskin.hackatontemplate.auth.domain.local.AuthDataStoreRepository
import bob.colbaskin.hackatontemplate.auth.domain.models.Client
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

    override suspend fun createClient() {
        val clientDTO = ClientDTO(
            name = "mobile_app",
            baseUrl = "base_url",
            allowedRedirectUrls = listOf("https://menoitami.ru/auth/redirect/"),
            type = 1
        )
        val clientId = authApiService.createClient(clientDTO)
        Log.d("Auth", "Client created: $clientId")

        authDataStoreRepository.saveClient(
            Client(
                clientId = clientId,
                name = clientDTO.name,
                baseUrl = clientDTO.baseUrl,
                allowedRedirectUrls = clientDTO.allowedRedirectUrls,
                type = clientDTO.type
            )
        )
    }
}