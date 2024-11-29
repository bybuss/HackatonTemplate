package bob.colbaskin.hackatontemplate.auth.domain

interface AuthRepository {

    fun isLoggedIn(): Boolean

    suspend fun register(email: String, password: String): Boolean

    suspend fun login(email: String, password: String): Boolean

    fun logout()

    suspend fun getToken(): String?
}