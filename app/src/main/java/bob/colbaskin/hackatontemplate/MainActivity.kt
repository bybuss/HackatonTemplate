package bob.colbaskin.hackatontemplate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import bob.colbaskin.hackatontemplate.navigation.graph.RootNavGraph
import bob.colbaskin.hackatontemplate.ui.theme.HackatonTemplateTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()
        setContent {
            HackatonTemplateTheme {
                RootNavGraph()
            }
        }
    }
}
