package bob.colbaskin.hackatontemplate.navigation.graph

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.*
import bob.colbaskin.hackatontemplate.AppScreen
import bob.colbaskin.hackatontemplate.navigation.Screen
import bob.colbaskin.hackatontemplate.onBoarding.presentation.OnBoardViewModel
import bob.colbaskin.hackatontemplate.onBoarding.presentation.WelcomeScreen
import com.google.accompanist.pager.ExperimentalPagerApi

/**
 * @author bybuss
 */

@OptIn(ExperimentalAnimationApi::class, ExperimentalPagerApi::class)
@Composable
fun RootNavGraph() {
    val navController = rememberNavController()
    val onBoardViewModel: OnBoardViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = onBoardViewModel.startDestination.value,
        route = Graph.ROOT
    ) {
        composable(route = Screen.Welcome.route) {
            WelcomeScreen(
                onNextClick = {
                    navController.popBackStack()
                    navController.navigate(Graph.AUTH) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                }
            )
        }

        authNavGraph(navController)

        composable(Graph.MAIN) {
            AppScreen()
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val AUTH = "auth_graph"
    const val MAIN = "main_graph"
    const val DETAILS = "details_graph"
}