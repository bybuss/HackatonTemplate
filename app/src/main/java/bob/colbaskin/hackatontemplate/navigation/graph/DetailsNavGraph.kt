package bob.colbaskin.hackatontemplate.navigation.graph

import android.content.Intent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import bob.colbaskin.hackatontemplate.auth.presentation.AuthViewModel
import bob.colbaskin.hackatontemplate.home.presentation.HomeScreenDetailed
import bob.colbaskin.hackatontemplate.navigation.DetailsScreen
import bob.colbaskin.hackatontemplate.onBoarding.presentation.WelcomeScreen
import bob.colbaskin.hackatontemplate.profile.presentation.ProfileScreenDetailed
import bob.colbaskin.hackatontemplate.webView.presentation.WebBrowser
import com.google.accompanist.pager.ExperimentalPagerApi
import javax.inject.Inject

/**
 * @author bybuss
 */

fun NavGraphBuilder.detailsNavGraph (navController: NavHostController) {
    navigation(
        startDestination = DetailsScreen.Home.route,
        route = Graph.DETAILS
    ) {
        composable(DetailsScreen.Home.route) {
            HomeScreenDetailed (
                onClick = {
                    navController.navigateUp()
                }
            )
        }
        composable(
            route = DetailsScreen.WebBrowser.route,
            deepLinks = listOf (
                navDeepLink {
                    uriPattern = "https://https://menoitami.ru/{code}"
                    action = Intent.ACTION_VIEW
                }
            ),
            arguments = listOf (
                navArgument("code") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { entry ->
            WebBrowser(
                onExitClick = {
                    navController.navigateUp()
                },
                redirectCode = entry.arguments?.getInt("code")
            )
        }
        composable(DetailsScreen.Profile.route) {
            ProfileScreenDetailed (
                onClick = {
                    navController.navigateUp()
                }
            )
        }
    }
}