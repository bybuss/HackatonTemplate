package bob.colbaskin.hackatontemplate.webViewTest.presentation

import android.net.Uri
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import bob.colbaskin.hackatontemplate.navigation.DetailsScreen

@Composable
fun WebBrowser(
    onExitClick: () -> Unit,
    titleText: Int
) {
    var url by remember { mutableStateOf("https://www.google.com") }
    var webViewInstance by remember { mutableStateOf<WebView?>(null) }
    var isWebViewVisible by remember { mutableStateOf(true) }
    var canGoBack by remember { mutableStateOf(false) }
    var canGoForward by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            BrowserTopBar(
                url = url,
                canGoBack = canGoBack,
                canGoForward = canGoForward,
                onUrlChange = { url = it },
                onBack = {
                    webViewInstance?.goBack()
                },
                onForward = {
                    webViewInstance?.goForward()
                },
                onRefresh = { webViewInstance?.reload() },
                onExit = {
                    webViewInstance?.destroy()
                    isWebViewVisible = false
                    onExitClick()
                },
                titleText = titleText,
            )
        }
    ) { innerPadding ->
        if (isWebViewVisible) {
            WebView(
                url = url.toUri(),
                onWebViewCreated = { webView ->
                    webViewInstance = webView
                },
                onPageFinished = { webView ->
                    canGoBack = webView.canGoBack()
                    canGoForward = webView.canGoForward()
                    url = webView.url ?: url
                },
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrowserTopBar (
    url: String,
    canGoBack: Boolean,
    canGoForward: Boolean,
    onUrlChange: (String) -> Unit,
    onBack: () -> Unit,
    onForward: () -> Unit,
    onRefresh: () -> Unit,
    onExit:() -> Unit,
    titleText: Int
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
                        Text("$titleText")
                    }
                }
                Spacer(modifier = Modifier.padding(top = 4.dp))
                OutlinedTextField(
                    value = url,
                    onValueChange = onUrlChange,
                    modifier = Modifier
                        .fillMaxWidth(0.97f)
                        .height(45.dp),
                    singleLine = true,
                    placeholder = { Text("Введите URL") },
                    textStyle = MaterialTheme.typography.bodySmall
                )
            }
        },
        expandedHeight = 115.dp
    )
}

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
fun WebViewScreenDetailedPreview() {
    WebBrowser ({}, 1)
}