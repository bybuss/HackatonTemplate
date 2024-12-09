package bob.colbaskin.hackatontemplate

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import bob.colbaskin.hackatontemplate.auth.presentation.WebBrowserViewModel
import bob.colbaskin.hackatontemplate.navigation.graph.RootNavGraph
import bob.colbaskin.hackatontemplate.onBoarding.presentation.OnBoardViewModel
import bob.colbaskin.hackatontemplate.ui.theme.HackatonTemplateTheme
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val onBoardViewModel: OnBoardViewModel by viewModels()

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        val status: String = if (isGranted) "получено" else "отказано"

        Toast.makeText(
            this,
            "Разрешение на местоположение $status",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MapKitFactory.setApiKey(BuildConfig.MAPKIT_API_KEY)
        MapKitFactory.initialize(this)

        enableEdgeToEdge()

        checkLocationPermission()

        installSplashScreen().setKeepOnScreenCondition {
            onBoardViewModel.isLoading.value
        }

        setContent {
            HackatonTemplateTheme {
                RootNavGraph()
            }
        }
    }

    private fun checkLocationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {}
            else -> {
                locationPermissionRequest.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
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