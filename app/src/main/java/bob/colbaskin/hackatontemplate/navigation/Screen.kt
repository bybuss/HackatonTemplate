package bob.colbaskin.hackatontemplate.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.WebAsset
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
    object Map: Screen("map", "Карта", Icons.Filled.Map)
    object Welcome: DetailsScreen("welcome")
}

sealed class AuthScreen(val route: String) {
//    object Login : AuthScreen(route = "login")
//    object SignUp : AuthScreen(route = "sign_up")
//    object Forgot : AuthScreen(route = "forgot")
    object WebBrowser: AuthScreen("web_browser")
}

sealed class DetailsScreen(val route: String) {
    object Home: DetailsScreen("home_details")
    object Profile: DetailsScreen("profile_details")
}