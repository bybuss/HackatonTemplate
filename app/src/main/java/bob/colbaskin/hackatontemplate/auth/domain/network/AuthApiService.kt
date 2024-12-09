package bob.colbaskin.hackatontemplate.auth.domain.network

import bob.colbaskin.hackatontemplate.auth.data.models.CodeToTokenDTO
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("code-to-token")
    suspend fun codeToToken(@Body request: CodeToTokenDTO): String
}