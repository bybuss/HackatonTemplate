package bob.colbaskin.hackatontemplate.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import bob.colbaskin.hackatontemplate.auth.presentation.ForgotScreen
import bob.colbaskin.hackatontemplate.auth.presentation.LoginScreen
import bob.colbaskin.hackatontemplate.auth.presentation.SignUpScreen
import bob.colbaskin.hackatontemplate.navigation.AuthScreen


/**
 * @author bybuss
 */

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.AUTH,
        startDestination = AuthScreen.Login.route
    ) {
        composable(route = AuthScreen.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(route = AuthScreen.SignUp.route) {
            SignUpScreen(navController = navController)
        }
        composable(route = AuthScreen.Forgot.route) {
            ForgotScreen(navController = navController)
        }
    }
}