package bob.colbaskin.hackatontemplate.navigation.graph

import android.content.Intent
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import bob.colbaskin.hackatontemplate.navigation.AuthScreen
import bob.colbaskin.hackatontemplate.auth.presentation.WebBrowser


/**
 * @author bybuss
 */

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        startDestination = AuthScreen.WebBrowser.route,
        route = Graph.AUTH
    ) {

        composable(
            route = AuthScreen.WebBrowser.route,
            deepLinks = listOf (
                navDeepLink {
                    uriPattern = "https://menoitami.ru/auth/redirect/{code}"
                    action = Intent.ACTION_VIEW
                }
            ),
            arguments = listOf (
                navArgument("code") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { backStackEntry ->
            WebBrowser(
                onExitClick = {
                    navController.navigateUp()
                },
                redirectCode = backStackEntry.arguments?.getInt("code")
            )
        }
    }
}