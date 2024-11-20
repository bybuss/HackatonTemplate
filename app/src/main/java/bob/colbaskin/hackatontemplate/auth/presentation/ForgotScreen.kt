package bob.colbaskin.hackatontemplate.auth.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import bob.colbaskin.hackatontemplate.navigation.AuthScreen

/**
 * @author bybuss
 */
@Composable
fun ForgotScreen(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Home Screen",
            textAlign = TextAlign.Center,
            modifier = Modifier.clickable { navController.navigate(AuthScreen.Login.route) }
        )
    }
}