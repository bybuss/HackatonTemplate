package bob.colbaskin.hackatontemplate

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import bob.colbaskin.hackatontemplate.navigation.graph.RootNavGraph
import bob.colbaskin.hackatontemplate.onBoarding.presentation.OnBoardViewModel
import bob.colbaskin.hackatontemplate.ui.theme.HackatonTemplateTheme
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.mapview.MapView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var mapView: MapView
    private val onBoardViewModel: OnBoardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey(BuildConfig.MAPKIT_API_KEY)
        MapKitFactory.initialize(this)

        enableEdgeToEdge()
        installSplashScreen().setKeepOnScreenCondition {
            onBoardViewModel.isLoading.value
        }

        mapView = MapView(this)

        setContent {
            HackatonTemplateTheme {
                RootNavGraph()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }
}