package bob.colbaskin.hackatontemplate.auth.presentation

import android.util.Log
import android.webkit.WebView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bob.colbaskin.hackatontemplate.BuildConfig
import bob.colbaskin.hackatontemplate.auth.domain.local.AuthDataStoreRepository
import bob.colbaskin.hackatontemplate.auth.domain.network.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.security.SecureRandom
import java.util.Base64
import javax.inject.Inject

@HiltViewModel
class WebBrowserViewModel @Inject constructor(
    private val authDataStoreRepository: AuthDataStoreRepository,
    private val authRepository: AuthRepository
): ViewModel() {

    private val _redirectCode = MutableStateFlow<Int?>(null)
    var redirectCode = _redirectCode.asStateFlow()

    private var page = "login"
    private var codeVerifier: String? = null
    private var codeChallengeMethod = "S256"
    private var clientId = "3"
    private var redirectUrl = "https://https://menoitami.ru/1234"

    private val _url = MutableStateFlow(
        "${BuildConfig.BASE_API_URL}/pages/$page.html?" +
                "code_verifier=$codeVerifier&" +
                "code_challenge_method=$codeChallengeMethod&" +
                "client_id=$clientId&" +
                "redirect_url=$redirectUrl"
    )
    val url = _url.asStateFlow()

    private var webViewInstance: WebView? = null

    private var _isWebViewVisible = MutableStateFlow(true)
    val isWebViewVisible = _isWebViewVisible.asStateFlow()

    private var _canGoBack = MutableStateFlow(false)
    val canGoBack = _canGoBack.asStateFlow()

    private var _canGoForward = MutableStateFlow(false)
    val canGoForward = _canGoForward.asStateFlow()

    init {
        viewModelScope.launch {
            codeVerifier = getOrGenerateCodeVerifier()
            //tokenDataStoreRepository.clearCodeVerifier()
            createClientIfNotExists()
            authDataStoreRepository.clearClient()
        }
    }

    private suspend fun getOrGenerateCodeVerifier(): String {
        val savedCodeVerifier = authDataStoreRepository.getCodeVerifier().first()

        return if (!savedCodeVerifier.isNullOrEmpty()) {
            Log.d("WebView", "Get codeVerifier: $savedCodeVerifier")
            savedCodeVerifier
        } else {
            val generatedCodeVerifier = generateCodeVerifier()
            authDataStoreRepository.saveCodeVerifier(generatedCodeVerifier)
            Log.d("WebView", "Generated codeVerifier: $generatedCodeVerifier")
            generatedCodeVerifier
        }
    }

    private fun generateCodeVerifier(): String {
        val randomBytes = ByteArray(32)
        SecureRandom().nextBytes(randomBytes)
        val sha256Hash = MessageDigest.getInstance("SHA-256").digest(randomBytes)
        return Base64.getUrlEncoder().withoutPadding().encodeToString(sha256Hash)
    }

    private suspend fun createClientIfNotExists() {
        val client = authDataStoreRepository.getClient().first()

        if (client == null) authRepository.createClient()
    }

    fun onBack() {
        webViewInstance?.goBack()
    }

    fun onForward() {
        webViewInstance?.goForward()
    }

    fun onRefresh() {
        webViewInstance?.reload()
    }

    fun onExit() {
        webViewInstance?.destroy()
        _isWebViewVisible.value = false
    }

    fun updateWebViewInstance(webView: WebView) {
        webViewInstance = webView
    }

    fun onPageFinished(webView: WebView) {
        _canGoBack.value = webView.canGoBack()
        _canGoForward.value = webView.canGoForward()
        _url.value = webView.url ?: _url.value
    }

    fun updateRedirectCode(newCode: Int?) {
        _redirectCode.value = newCode
        Log.d("WebView", "viewmodel: $newCode")
    }
}