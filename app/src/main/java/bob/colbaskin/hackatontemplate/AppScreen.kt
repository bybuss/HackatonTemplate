package bob.colbaskin.hackatontemplate

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import bob.colbaskin.hackatontemplate.navigation.BottomNavBar
import bob.colbaskin.hackatontemplate.navigation.graph.MainNavGraph

/**
 * @author bybuss
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppScreen(navController: NavHostController = rememberNavController()) {
    Scaffold (
        bottomBar = { BottomNavBar(navController = navController) }
    ) {
        MainNavGraph(navController = navController)
    }
}