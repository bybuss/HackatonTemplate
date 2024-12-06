package bob.colbaskin.hackatontemplate.yandexMap.presentation

import android.graphics.PointF
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import bob.colbaskin.hackatontemplate.R
import com.yandex.mapkit.Animation
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider

@Suppress("DEPRECATION")
@Composable
fun YandexMapScreen(viewModel: YandexMapViewModel = hiltViewModel()) {
    val cameraPosition by viewModel.cameraPosition.collectAsState()
    val placemarks by viewModel.placemarks.collectAsState()

    AndroidView(
        factory = { context ->
            MapView(context).apply {
                Log.d("YandexMap", "Map is initialized")
                map.move(
                    cameraPosition,
                    Animation(Animation.Type.SMOOTH, 1f),
                    null
                )

                map.mapObjects.addTapListener { _, point ->
                    viewModel.addPlacemark(point)
                    true
                }

                val placemark = map.mapObjects.addPlacemark().apply {
                    geometry = Point(47.235713, 39.701505)
                    setIcon(ImageProvider.fromResource(context, R.drawable.pin))
                }
                placemark.setText("Special place")
                placemark.setIconStyle(
                    IconStyle().apply {
                        anchor = PointF(0.5f, 1.0f)
                        scale = 0.6f
                        zIndex = 10f
                    }
                )
            }
        },
        modifier = Modifier.fillMaxSize(),
        update = { mapView ->
            Log.d("YandexMap", "Map is updated")
            mapView.map.move(
                cameraPosition,
                Animation(Animation.Type.SMOOTH, 1f),
                null
            )
            val placemark = mapView.map.mapObjects.addPlacemark().apply {
                geometry = Point(47.235713, 39.701505)
                setIcon(ImageProvider.fromResource(mapView.context, R.drawable.pin))
            }
            placemark.setText("Special place")
            placemark.setIconStyle(
                IconStyle().apply {
                    anchor = PointF(0.5f, 1.0f)
                    scale = 0.6f
                    zIndex = 10f
                }
            )

            val mapObjects = mapView.map.mapObjects

            //mapObjects.clear()

            val imageProvider = ImageProvider.fromResource(mapView.context, R.drawable.pin)
            placemarks.forEach { point ->
                @Suppress("DEPRECATION")
                mapObjects.addPlacemark(point, imageProvider).apply {
                    addTapListener { _, clickedPoint ->
                        Log.d("YandexMap", "Tapped the point (${clickedPoint.longitude}, ${clickedPoint.latitude})")
                        Toast.makeText(
                            mapView.context,
                            "Tapped the point (${clickedPoint.longitude}, ${clickedPoint.latitude})",
                            Toast.LENGTH_SHORT
                        ).show()
                        true
                    }
                }
            }

        }
    )
}