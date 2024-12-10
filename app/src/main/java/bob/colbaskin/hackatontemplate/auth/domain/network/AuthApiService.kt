package bob.colbaskin.hackatontemplate.auth.domain.network

import bob.colbaskin.hackatontemplate.auth.data.models.CodeToTokenDTO
import bob.colbaskin.hackatontemplate.auth.data.models.TokensDTO
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("code-to-token")
    suspend fun codeToToken(@Body request: CodeToTokenDTO): TokensDTO
}