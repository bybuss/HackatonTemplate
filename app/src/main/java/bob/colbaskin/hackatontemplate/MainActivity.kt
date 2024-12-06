package bob.colbaskin.hackatontemplate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import bob.colbaskin.hackatontemplate.navigation.graph.RootNavGraph
import bob.colbaskin.hackatontemplate.onBoarding.presentation.OnBoardViewModel
import bob.colbaskin.hackatontemplate.ui.theme.HackatonTemplateTheme
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val onBoardViewModel: OnBoardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey(BuildConfig.MAPKIT_API_KEY)
        MapKitFactory.initialize(this)

        enableEdgeToEdge()
        installSplashScreen().setKeepOnScreenCondition {
            onBoardViewModel.isLoading.value
        }

        setContent {
            HackatonTemplateTheme {
                RootNavGraph()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
    }

    override fun onStop() {
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }
}