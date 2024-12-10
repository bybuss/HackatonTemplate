package bob.colbaskin.hackatontemplate.auth.presentation

import android.util.Log
import android.webkit.WebView
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class WebBrowserViewModel @Inject constructor(): ViewModel() {

    private val _url = MutableStateFlow("")

    private var webViewInstance: WebView? = null

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

    fun updateWebViewInstance(webView: WebView) {
        webViewInstance = webView
    }

    fun updateUrl(url: String) {
        _url.value = url
        Log.d("WebView", "Url updated in WebBrowserViewModel: ${_url.value}")
    }

    fun onPageFinished(webView: WebView) {
        _canGoBack.value = webView.canGoBack()
        _canGoForward.value = webView.canGoForward()
        _url.value = webView.url ?: _url.value
    }
}