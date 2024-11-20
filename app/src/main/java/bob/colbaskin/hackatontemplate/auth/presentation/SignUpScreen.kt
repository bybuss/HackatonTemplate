package bob.colbaskin.hackatontemplate.auth.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import bob.colbaskin.hackatontemplate.navigation.AuthScreen
import bob.colbaskin.hackatontemplate.navigation.graph.Graph

/**
 * @author bybuss
 */
@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    val authState by authViewModel.authState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Authenticated -> {}
            is AuthState.Error -> Toast.makeText(
                context, (authState as AuthState.Error).message, Toast.LENGTH_SHORT
            ).show()
            else -> Unit
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Регистрация", fontSize = 32.sp)
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(label = "Firstname", value = firstName, onValueChange = { firstName = it })
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(label = "Lastname", value = lastName, onValueChange = { lastName = it })
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(label = "Address", value = address, onValueChange = { address = it })
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(label = "Email", value = email, onValueChange = { email = it })
        Spacer(modifier = Modifier.height(8.dp))
        CustomTextField(label = "Password", value = password, onValueChange = { password = it })
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                authViewModel.signUp(firstName, lastName, address, email, password)
                navController.navigate(Graph.MAIN) {
                    popUpTo(AuthScreen.SignUp.route) { inclusive = true }
                }
            },
            enabled = authState !is AuthState.Loading
        ) {
            Text(text = "Создать аккаунт")
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(
            onClick = { navController.navigate(AuthScreen.Login.route) }
        ) {
            Text("У меня уже есть аккаунт")
        }
    }
}