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
    private var codeChallenger: String? = null
    private val redirectUrl = "hack_app://return_app/?auth_code={auth_code}"
    private val codeChallengeMethod = "S256"
    private val clientId = "10"
    private val roleId = "2"

    private val _url = MutableStateFlow<String?>(null)
    val url: StateFlow<String?> = _url.asStateFlow()

    init {
        viewModelScope.launch {
            codeVerifier = getOrGenerateCodeVerifier()
            codeChallenger = codeVerifierToPKCE(codeVerifier!!)
            _url.value = "${BuildConfig.BASE_API_URL}/pages/login.html?" +
                    "code_verifier=$codeVerifier&" +
                    "code_challenge_method=$codeChallengeMethod&" +
                    "client_id=$clientId&" +
                    "redirect_url=$redirectUrl&" +
                    "role_id=$roleId"
            Log.d("Auth", "Initial url: ${_url.value}")
            checkAuthState()
        }
        Log.d("Auth", "Current authState: ${_authState.value}")
        Log.d("Auth", "Current CodeVerifier: $codeVerifier")
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
        val allowedCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-._~"
        val randomBytes = ByteArray(32)
        SecureRandom().nextBytes(randomBytes)

        val codeVerifier = StringBuilder()
        randomBytes.forEach { byte ->
            val charIndex = byte.toInt() and 0xFF
            codeVerifier.append(allowedCharacters[charIndex % allowedCharacters.length])
        }

        val verifier = codeVerifier.toString()
        return if (verifier.length < 43) {
            verifier.padEnd(43, '_')
        } else {
            verifier
        }
    }

    private fun codeVerifierToPKCE(codeVerifier: String): String {
        val sha256Hash = MessageDigest.getInstance("SHA-256").digest(codeVerifier.toByteArray())
        val challengerCode = Base64.getUrlEncoder().withoutPadding().encodeToString(sha256Hash)
        Log.d("Auth", "Challenger code: $challengerCode from codeVerifier: $codeVerifier" )
        return challengerCode
    }

    private fun checkAuthState() {
        if (authRepository.isLoggedIn()) {
            _authState.value = AuthState.Authenticated
        } else {
            _authState.value = AuthState.Unauthenticated
        }
        Log.d("Auth", "Checked authState: ${_authState.value}")
    }

    fun codeToToken(code: String) {
        viewModelScope.launch {
            try {
                authRepository.codeToToken(
                    CodeToTokenDTO(
                        authCode = code,
                        codeChallenger = codeChallenger ?: "null",
                        redirectUrl = redirectUrl,
                        scopes = listOf("string")
                    )
                )
                checkAuthState()
            } catch (e: Exception) {
                Log.e("Auth","Change code to token error: $e")
                Log.e("Auth", "CodeToTokenDTO: ${
                    CodeToTokenDTO(
                        authCode = code,
                        codeChallenger = codeChallenger ?: "null",
                        redirectUrl = redirectUrl,
                        scopes = listOf("string")
                    )
                }")
            }
        }
    }
}