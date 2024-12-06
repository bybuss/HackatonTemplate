package bob.colbaskin.hackatontemplate.auth.presentation

import android.widget.Toast
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch

/**
 * @author bybuss
 */
@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = hiltViewModel(),
    onWebViewClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    val email by authViewModel.email.collectAsState()
    val password by authViewModel.password.collectAsState()
    val authState by authViewModel.authState.collectAsState()
    val context = LocalContext.current

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val keyboardHeight = WindowInsets.ime.getBottom(LocalDensity.current)

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Authenticated -> {}
            is AuthState.Error -> Toast.makeText(
                context, (authState as AuthState.Error).message, Toast.LENGTH_SHORT
            ).show()
            else -> Unit
        }
    }

    LaunchedEffect(keyboardHeight) {
        coroutineScope.launch {
            scrollState.scrollBy(keyboardHeight.toFloat())
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .imePadding()
            .systemBarsPadding()
    ) {
        Text(text = "Регистрация", fontSize = 32.sp)
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(
            label = "Email",
            value = email,
            onValueChange = { authViewModel.setEmail(it) }
        )
        Spacer(modifier = Modifier.height(8.dp))
        CustomTextField(
            label = "Password",
            value = password,
            onValueChange = {
                authViewModel.setPassword(it) }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                authViewModel.signUp(email, password)
                onSignUpClick()
            },
            enabled = authState !is AuthState.Loading
        ) {
            Text(text = "Создать аккаунт")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                authViewModel.login(email, password)
                onWebViewClick()
            },
            enabled = authState !is AuthState.Loading
        ) {
            Text(text = "WebView")
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(
            onClick = { onLoginClick() }
        ) {
            Text("У меня уже есть аккаунт")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen(
        onWebViewClick = { },
        onSignUpClick = { },
        onLoginClick = { },
    )
}