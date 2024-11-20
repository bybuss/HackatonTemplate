package bob.colbaskin.hackatontemplate.auth.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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
class AuthViewModel @Inject constructor() : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    init {
        checkAuthState()
    }

    fun checkAuthState() {
//        if (authRepository.isLoggedIn()) {
//            _authState.value = AuthState.Authenticated
//        } else {
//            _authState.value = AuthState.Unauthenticated
//        }
        Log.d("AuthViewModel", "checkAuthState")
    }

    fun login(email: String, password: String) {
        Log.d("AuthViewModel", "login")
    }


    fun signUp(
        firstName: String,
        lastName: String,
        address: String,
        email: String,
        password: String
    ) {
        Log.d("AuthViewModel", "signup")
    }

    fun signOut() {
        Log.d("AuthViewModel", "signout")
    }
}