package bob.colbaskin.hackatontemplate.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import bob.colbaskin.hackatontemplate.auth.presentation.ForgotScreen
import bob.colbaskin.hackatontemplate.auth.presentation.LoginScreen
import bob.colbaskin.hackatontemplate.auth.presentation.SignUpScreen
import bob.colbaskin.hackatontemplate.navigation.AuthScreen
import bob.colbaskin.hackatontemplate.navigation.DetailsScreen


/**
 * @author bybuss
 */

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        startDestination = AuthScreen.Login.route,
        route = Graph.AUTH
    ) {
        composable(route = AuthScreen.Login.route) {
            LoginScreen(
                onWebViewClick = { navController.navigate(DetailsScreen.WebBrowser.route) },
                onSignUpClick = { navController.navigate(AuthScreen.SignUp.route) },
                onForgotClick = { navController.navigate(AuthScreen.Forgot.route) },
                onLoginClick = {
                    navController.navigate(Graph.MAIN) {
                        popUpTo(AuthScreen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        composable(route = AuthScreen.SignUp.route) {
            SignUpScreen(
                onWebViewClick = { navController.navigate(DetailsScreen.WebBrowser.route) },
                onSignUpClick = {
                    navController.navigate(Graph.MAIN) {
                        popUpTo(AuthScreen.Login.route) { inclusive = true }
                    }
                },
                onLoginClick = { navController.navigate(AuthScreen.Login.route) },
            )
        }
        composable(route = AuthScreen.Forgot.route) {
            ForgotScreen(navController = navController)
        }
    }

    detailsNavGraph(navController)
}