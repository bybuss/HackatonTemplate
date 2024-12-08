package bob.colbaskin.hackatontemplate.yandexMap.presentation

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import bob.colbaskin.hackatontemplate.R
import bob.colbaskin.hackatontemplate.yandexMap.data.models.GreenFootprint
import com.yandex.mapkit.Animation
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider

@Suppress("DEPRECATION")
@Composable
fun YandexMapScreen(viewModel: YandexMapViewModel = hiltViewModel()) {
    val cameraPosition by viewModel.cameraPosition.collectAsState()
    val placemarks by viewModel.placemarks.collectAsState()
    val selectedFootprint by viewModel.selectedPlacemark.collectAsState()

    AndroidView(
        factory = { context ->
            MapView(context).apply {
                Log.d("YandexMap", "Map is initialized")
                map.move(
                    cameraPosition,
                    Animation(Animation.Type.SMOOTH, 1f),
                    null
                )
            }
        },
        modifier = Modifier.fillMaxSize(),
        update = { mapView ->
            Log.d("YandexMap", "Map is updated")
            val mapObjects = mapView.map.mapObjects
            val imageProvider = ImageProvider.fromResource(mapView.context, R.drawable.pin)

            placemarks.forEach { footprint ->
                val placemark = mapObjects.addPlacemark(
                    Point(footprint.latitude, footprint.longitude),
                    imageProvider
                ).apply {
                    setText(footprint.name)
                    userData = footprint
                    addTapListener { mapObject, _ ->
                        Log.d("YandexMap", "Tapped the point: ${footprint.name}")
                        val selectedPoint = mapObject.userData as? GreenFootprint
                        viewModel.updateSelectedPlacemark(selectedPoint)
                        true
                    }
                }
            }
        }
    )

    selectedFootprint?.let {
        ShowFootprintDialog(
            placemark = it,
            onDismiss = { viewModel.updateSelectedPlacemark(null) }
        )
    }
}

@Composable
fun ShowFootprintDialog(
    placemark: GreenFootprint,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = placemark.name) },
        text = { Text(text = placemark.description) },
        buttons = {
            Button(onClick = onDismiss) {
                Text("Закрыть")
            }
        }
    )
}