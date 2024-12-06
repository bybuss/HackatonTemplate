package bob.colbaskin.hackatontemplate.navigation.graph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import bob.colbaskin.hackatontemplate.auth.presentation.AuthViewModel
import bob.colbaskin.hackatontemplate.home.presentation.HomeScreen
import bob.colbaskin.hackatontemplate.navigation.DetailsScreen
import bob.colbaskin.hackatontemplate.navigation.Screen
import bob.colbaskin.hackatontemplate.profile.presentation.ProfileScreen
import bob.colbaskin.hackatontemplate.yandexMap.presentation.YandexMapScreen

/**
 * @author bybuss
 */
@Composable
fun MainNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        route = Graph.MAIN
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen {
                navController.navigate(DetailsScreen.Home.route)
            }
        }
        composable(route = Screen.Map.route) {
            YandexMapScreen()
        }
        composable(route = Screen.Profile.route) {
            ProfileScreen {
                navController.navigate(DetailsScreen.Profile.route)
            }
        }

        detailsNavGraph(navController)
    }
}