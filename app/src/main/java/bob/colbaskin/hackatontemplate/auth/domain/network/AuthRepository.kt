package bob.colbaskin.hackatontemplate.auth.domain.network

import bob.colbaskin.hackatontemplate.auth.data.models.CodeToTokenDTO

interface AuthRepository {

    fun isLoggedIn(): Boolean

    suspend fun codeToToken(request: CodeToTokenDTO)
}