package bob.colbaskin.hackatontemplate.auth.domain.local

import kotlinx.coroutines.flow.Flow

interface TokenDataStoreRepository {

    suspend fun saveToken(token: String)

    fun getToken(): Flow<String?>
}