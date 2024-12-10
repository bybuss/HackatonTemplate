package bob.colbaskin.hackatontemplate.auth.presentation

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri

@Composable
fun WebBrowser(
    webBrowserViewModel: WebBrowserViewModel,
    authViewModel: AuthViewModel
) {
    val url by authViewModel.url.collectAsState()
    val canGoBack by webBrowserViewModel.canGoBack.collectAsState()
    val canGoForward by webBrowserViewModel.canGoForward.collectAsState()

    LaunchedEffect(url) {
        url?.let { webBrowserViewModel.updateUrl(it) }
    }

    Scaffold(
        topBar = {
            BrowserTopBar(
                canGoBack = canGoBack,
                canGoForward = canGoForward,
                onBack = { webBrowserViewModel.onBack() },
                onForward = { webBrowserViewModel.onForward() },
                onRefresh = { webBrowserViewModel.onRefresh() }
            )
        }
    ) { innerPadding ->
        url?.toUri()?.let {
            WebView(
                url = it,
                onWebViewCreated = { webView ->
                    webBrowserViewModel.updateWebViewInstance(webView)
                },
                onPageFinished = { webView ->
                    webBrowserViewModel.onPageFinished(webView)
                },
                onAuthCodeReceived = { authCode ->
                    authViewModel.codeToToken(authCode)
                    Log.d("Auth", "Received auth_code from webView: $authCode")
                },
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrowserTopBar (
    canGoBack: Boolean,
    canGoForward: Boolean,
    onBack: () -> Unit,
    onForward: () -> Unit,
    onRefresh: () -> Unit
) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        title = {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(end = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row {
                        IconButton(onClick = onBack, enabled = canGoBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Назад"
                            )
                        }
                        IconButton(onClick = onForward, enabled = canGoForward) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = "Вперёд"
                            )
                        }
                    }
                    IconButton(onClick = onRefresh) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Обновить"
                        )
                    }
                }
            }
        }
    )
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebView(
    url: Uri,
    onWebViewCreated: (WebView) -> Unit,
    onPageFinished: (WebView) -> Unit,
    onAuthCodeReceived: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    AndroidView (
        factory = {
            WebView(it).apply {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                settings.databaseEnabled = true
                settings.useWideViewPort = true
                settings.loadWithOverviewMode = true
                settings.cacheMode = WebSettings.LOAD_DEFAULT
                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {
                        val mobileAppUrl = request?.url.toString()
                        Log.d("WebView", "Current WebView url: $mobileAppUrl")
                        if (mobileAppUrl.startsWith("https://menoitami.ru/pages/hack_app://")) {
                            val uri = Uri.parse(mobileAppUrl)
                            val authCode = uri.getQueryParameter("auth_code")
                            Log.d("WebView", "Detected redirect url. Auth code: $authCode, uri: $uri")

                            authCode?.let { code ->
                                val cleanedCode =
                                    if (authCode.endsWith("/")) code.dropLast(1) else code
                                Log.d("WebView", "Cleaned Auth code: $cleanedCode")

                                onAuthCodeReceived(cleanedCode)
                            }
                            return true
                        }
                        return super.shouldOverrideUrlLoading(view, request)
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        if (view != null) {
                            onPageFinished(view)
                        }
                    }
                }
                onWebViewCreated(this)
                loadUrl(url.toString())
            }
        },
        update = { webView ->
            val currentUrl = webView.url
            if (currentUrl != url.toString()) {
                if (
                    url.toString().startsWith("http://") ||
                    url.toString().startsWith("https://")
                ) {
                    webView.loadUrl(url.toString())
                } else {
                    webView.loadUrl("https://$url")
                }
            }
        },
        modifier = modifier.fillMaxSize()
    )
}

@Preview
@Composable
fun BrowserTopBarPreview() {
    BrowserTopBar(
        canGoBack = true,
        canGoForward = true,
        onBack = {},
        onForward = {},
        onRefresh = {}
    )
}

@Preview
@Composable
fun WebViewStructurePreview() {
    WebView(
        url = "https://menoitami.ru/pages/reg.html".toUri(),
        onWebViewCreated = {},
        onAuthCodeReceived = {},
        onPageFinished = {}
    )
}
