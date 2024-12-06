package bob.colbaskin.hackatontemplate.yandexMap.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class YandexMapViewModel @Inject constructor() : ViewModel() {

    private val _cameraPosition = MutableStateFlow(
        CameraPosition(Point(47.235713, 39.701505), 14f, 0f, 0f)
    )
    val cameraPosition: StateFlow<CameraPosition> = _cameraPosition.asStateFlow()

    private val _placemarks = MutableStateFlow<List<Point>>(emptyList())
    val placemarks = _placemarks.asStateFlow()

    fun addPlacemark(point: Point) {
        Log.d("YandexMap", "Adding placemark at: ${point.latitude}, ${point.longitude}")
        _placemarks.value += point
    }
}
