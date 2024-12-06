package bob.colbaskin.hackatontemplate.auth.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import bob.colbaskin.hackatontemplate.auth.domain.local.TokenDataStoreRepository
import bob.colbaskin.hackatontemplate.auth.domain.network.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * @author bybuss
 */
sealed class AuthState {
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    data class Error(val message: String) : AuthState()
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenDataStoreRepository: TokenDataStoreRepository
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    init {
        checkAuthState()
    }

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    private fun checkAuthState() {
        if (authRepository.isLoggedIn()) {
            _authState.value = AuthState.Authenticated
        } else {
            _authState.value = AuthState.Unauthenticated
        }
    }

    fun login(email: String, password: String) {
        Log.d("AuthViewModel", "login")
//        runBlocking {
//            tokenDataStoreRepository.saveToken("token")
//        }
    }


    fun signUp(
        email: String,
        password: String
    ) {
        Log.d("AuthViewModel", "signup")
    }

    fun signOut() {
        Log.d("AuthViewModel", "signout")
    }
}