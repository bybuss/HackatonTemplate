package bob.colbaskin.hackatontemplate.auth.domain.network

import bob.colbaskin.hackatontemplate.auth.data.models.ClientDTO
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("/client/")
    suspend fun createClient(@Body request: ClientDTO): Int
}