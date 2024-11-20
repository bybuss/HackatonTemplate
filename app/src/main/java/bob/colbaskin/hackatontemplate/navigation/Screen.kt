package bob.colbaskin.hackatontemplate.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * @author bybuss
 */
sealed class Screen (
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    object Home: Screen("home", "Главная", Icons.Default.Home)
    object Profile: Screen("profile", "Профиль", Icons.Default.Person)
}

sealed class AuthScreen(val route: String) {
    object Splash : AuthScreen(route = "splash")
    object Login : AuthScreen(route = "lohin")
    object SignUp : AuthScreen(route = "sign_up")
    object Forgot : AuthScreen(route = "forgot")
}

sealed class DetailsScreen(val route: String) {
    object Home: DetailsScreen("home_details")
    object Profile: DetailsScreen("profile_details")
}