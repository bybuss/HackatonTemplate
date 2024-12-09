package bob.colbaskin.hackatontemplate.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import bob.colbaskin.hackatontemplate.auth.presentation.AuthenticationScreen
import bob.colbaskin.hackatontemplate.navigation.AuthScreen


/**
 * @author bybuss
 */

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        startDestination = AuthScreen.AuthenticationScreen.route,
        route = Graph.AUTH
    ) {

        composable(route = AuthScreen.AuthenticationScreen.route) {
            AuthenticationScreen(
                onUserAuthenticated = {
                    navController.navigate(Graph.MAIN) {
                        popUpTo(Graph.AUTH) { inclusive = true }
                    }
                }
            )
        }
    }
}