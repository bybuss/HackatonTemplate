package bob.colbaskin.hackatontemplate.navigation.graph

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import bob.colbaskin.hackatontemplate.AppScreen
import bob.colbaskin.hackatontemplate.home.presentation.HomeScreen

/**
 * @author bybuss
 */

@Composable
fun RootNavGraph() {
    val navController = rememberNavController()

    NavHost (
        navController = navController,
        startDestination = Graph.AUTH,
        route = Graph.ROOT
    ) {
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