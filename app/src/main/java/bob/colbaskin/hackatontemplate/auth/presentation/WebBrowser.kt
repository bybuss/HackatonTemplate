package bob.colbaskin.hackatontemplate.auth.presentation

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun WebBrowser(
    onExitClick: () -> Unit,
    viewModel: WebBrowserViewModel = hiltViewModel(),
    redirectCode: Int? = null
) {
    val url by viewModel.url.collectAsState()
    val canGoBack by viewModel.canGoBack.collectAsState()
    val canGoForward by viewModel.canGoForward.collectAsState()
    val isWebViewVisible by viewModel.isWebViewVisible.collectAsState()

    LaunchedEffect(redirectCode) {
        viewModel.updateRedirectCode(redirectCode)
    }

    Scaffold(
        topBar = {
            BrowserTopBar(
                canGoBack = canGoBack,
                canGoForward = canGoForward,
                onBack = { viewModel.onBack() },
                onForward = { viewModel.onForward() },
                onRefresh = { viewModel.onRefresh() },
                onExit = {
                    viewModel.onExit()
                    onExitClick()
                }
            )
        }
    ) { innerPadding ->
        if (isWebViewVisible) {
            WebView(
                url = url.toUri(),
                onWebViewCreated = { webView ->
                    viewModel.updateWebViewInstance(webView)
                },
                onPageFinished = { webView ->
                    viewModel.onPageFinished(webView)
                },
                modifier = Modifier.padding(innerPadding)
            )
        }
        else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
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
    onRefresh: () -> Unit,
    onExit:() -> Unit
) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        title = {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
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
                    Row {
                        IconButton(onClick = onRefresh) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Обновить"
                            )
                        }
                        IconButton(onClick = { onExit() }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Закрыть"
                            )
                        }
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
                        return false
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
fun WebViewScreenPreview() {
    WebBrowser ({})
}

@Preview
@Composable
fun BrowserTopBarPreview() {
    BrowserTopBar(
        canGoBack = true,
        canGoForward = true,
        onBack = {},
        onForward = {},
        onRefresh = {},
        onExit = {}
    )
}

@Preview
@Composable
fun WebViewStructurePreview() {
    WebView(
        url = "https://menoitami.ru/pages/reg.html".toUri(),
        onWebViewCreated = {},
        onPageFinished = {}
    )
}
