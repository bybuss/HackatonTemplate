package bob.colbaskin.hackatontemplate.auth.data

import bob.colbaskin.hackatontemplate.auth.domain.AuthRepository

class AuthRepositoryImpl (): AuthRepository {

    override fun isLoggedIn(): Boolean {
        return true
    }

    override suspend fun register(email: String, password: String): Boolean {
        return true
    }

    override suspend fun login(email: String, password: String): Boolean {
        return true
    }

    override fun logout() {}

    override suspend fun getToken(): String? {
        return "token"
    }
}