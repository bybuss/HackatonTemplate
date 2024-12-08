package bob.colbaskin.hackatontemplate.auth.domain.network

interface AuthRepository {

    fun isLoggedIn(): Boolean

    suspend fun createClient()
}