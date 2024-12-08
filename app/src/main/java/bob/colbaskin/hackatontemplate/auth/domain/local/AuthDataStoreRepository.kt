package bob.colbaskin.hackatontemplate.auth.domain.local

import bob.colbaskin.hackatontemplate.auth.domain.models.Client
import kotlinx.coroutines.flow.Flow

interface AuthDataStoreRepository {

    suspend fun saveToken(token: String)

    fun getToken(): Flow<String?>

    suspend fun saveCodeVerifier(codeVerifier: String)

    fun getCodeVerifier(): Flow<String?>

    suspend fun clearCodeVerifier()

    suspend fun saveClient(client: Client)

    fun getClient(): Flow<Client?>

    suspend fun clearClient()
}