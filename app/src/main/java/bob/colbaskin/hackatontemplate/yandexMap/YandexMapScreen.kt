package bob.colbaskin.hackatontemplate.yandexMap

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.mapview.MapView

@Composable
fun YandexMapScreen(viewModel: YandexMapViewModel = hiltViewModel()) {
    val cameraPosition = viewModel.cameraPosition.collectAsState()

    AndroidView(
        factory = { context ->
            // Создаем MapView в композабле
            MapView(context).apply {
                // Тут можно сделать начальную настройку карты, если нужно
            }
        },
        modifier = Modifier.fillMaxSize(),
        update = { mapView ->
            // Обновляем положение камеры, когда оно изменяется в ViewModel
            mapView.map.move(
                cameraPosition.value,
                Animation(Animation.Type.SMOOTH, 1f),
                null
            )
        }
    )
}

