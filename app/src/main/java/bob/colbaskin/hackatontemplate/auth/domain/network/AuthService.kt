package bob.colbaskin.hackatontemplate.auth.domain.network

import bob.colbaskin.hackatontemplate.auth.data.models.LoginRequest
import bob.colbaskin.hackatontemplate.auth.data.models.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("/register")
    suspend fun register(@Body request: RegisterRequest)

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest)
}