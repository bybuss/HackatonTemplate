package bob.colbaskin.hackatontemplate.auth.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bob.colbaskin.hackatontemplate.BuildConfig
import bob.colbaskin.hackatontemplate.auth.data.models.CodeToTokenDTO
import bob.colbaskin.hackatontemplate.auth.domain.local.AuthDataStoreRepository
import bob.colbaskin.hackatontemplate.auth.domain.network.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.security.SecureRandom
import java.util.Base64
import javax.inject.Inject

sealed class AuthState {
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    data class Error(val message: String) : AuthState()
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authDataStoreRepository: AuthDataStoreRepository,
    private val authRepository: AuthRepository
): ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    private var codeVerifier: String? = null
    private val redirectUrl = "hack_app://return_app/?auth_code={auth_code}"
    private val codeChallengeMethod = "S256"
    private val clientId = "10"

    private val _url = MutableStateFlow(
        "${BuildConfig.BASE_API_URL}/pages/login.html?" +
                "code_verifier=$codeVerifier&" +
                "code_challenge_method=$codeChallengeMethod&" +
                "client_id=$clientId&" +
                "redirect_url=$redirectUrl"
    )
    val url = _url.asStateFlow()

    init {
        viewModelScope.launch {
            codeVerifier = getOrGenerateCodeVerifier()
            //TODO: ВЕРНУТЬ ПОТОМ checkAuthState()
            //checkAuthState()
        }
        Log.d("Auth", "Current authState: ${_authState.value}")
    }

    private suspend fun getOrGenerateCodeVerifier(): String {
        val savedCodeVerifier = authDataStoreRepository.getCodeVerifier().first()

        return if (!savedCodeVerifier.isNullOrEmpty()) {
            Log.d("Auth", "Get codeVerifier: $savedCodeVerifier")
            savedCodeVerifier
        } else {
            val generatedCodeVerifier = generateCodeVerifier()
            authDataStoreRepository.saveCodeVerifier(generatedCodeVerifier)
            Log.d("Auth", "Generated codeVerifier: $generatedCodeVerifier")
            generatedCodeVerifier
        }
    }

    private fun generateCodeVerifier(): String {
        val randomBytes = ByteArray(32)
        SecureRandom().nextBytes(randomBytes)
        val sha256Hash = MessageDigest.getInstance("SHA-256").digest(randomBytes)
        return Base64.getUrlEncoder().withoutPadding().encodeToString(sha256Hash)
    }

    private fun checkAuthState() {
        _authState.value = AuthState.Loading
        //TODO: ВЕРНУТ�� ПОТОМ checkAuthState()
//        if (authRepository.isLoggedIn()) {
//            _authState.value = AuthState.Authenticated
//        } else {
//            _authState.value = AuthState.Unauthenticated
//        }
        _authState.value = AuthState.Authenticated
    }

    fun codeToToken(code: String) {
        checkAuthState()
        viewModelScope.launch {
            try {
                val token = authRepository.codeToToken(
                    CodeToTokenDTO(
                        authCode = code,
                        codeChallenger = codeVerifier!!,
                        redirectUrl = redirectUrl,
                        scopes = listOf("string")
                    )
                )
                authDataStoreRepository.saveToken(token)
                // FIXME: ПРОВЕРЯТЬ ПОТОМ ЧЕРЕЗ checkAuthState()
                //checkAuthState()
                Log.d("Auth", "Try change code to token: $token")
            } catch (e: Exception) {
                Log.e("Auth","Change code to token error: $e")
                Log.e("Auth", "CodeToTokenDTO: ${
                    CodeToTokenDTO(
                        authCode = code,
                        codeChallenger = codeVerifier ?: "null",
                        redirectUrl = redirectUrl,
                        scopes = listOf("string")
                    )
                }")
            }
        }
    }
}