package bob.colbaskin.hackatontemplate.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import bob.colbaskin.hackatontemplate.home.presentation.HomeScreenDetailed
import bob.colbaskin.hackatontemplate.navigation.DetailsScreen
import bob.colbaskin.hackatontemplate.profile.presentation.ProfileScreenDetailed

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
        composable(DetailsScreen.Profile.route) {
            ProfileScreenDetailed (
                onClick = {
                    navController.navigateUp()
                }
            )
        }
    }
}