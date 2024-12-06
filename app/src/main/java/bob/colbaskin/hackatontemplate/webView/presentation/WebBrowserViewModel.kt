package bob.colbaskin.hackatontemplate.webView.presentation

import android.util.Log
import android.webkit.WebView
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class WebBrowserViewModel @Inject constructor(): ViewModel() {

    private val _redirectCode = MutableStateFlow<Int?>(null)
    var redirectCode = _redirectCode.asStateFlow()

    private var _url = MutableStateFlow("https://www.google.com")
    val url = _url.asStateFlow()

    private var webViewInstance: WebView? = null

    private var _isWebViewVisible = MutableStateFlow(true)
    val isWebViewVisible = _isWebViewVisible.asStateFlow()

    private var _canGoBack = MutableStateFlow(false)
    val canGoBack = _canGoBack.asStateFlow()

    private var _canGoForward = MutableStateFlow(false)
    val canGoForward = _canGoForward.asStateFlow()


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

    fun setUrl(url: String) {
        _url.value = url
    }

    fun updateRedirectCode(newCode: Int?) {
        _redirectCode.value = newCode
        Log.d("rCode", "viewmodel: $newCode")
    }
}