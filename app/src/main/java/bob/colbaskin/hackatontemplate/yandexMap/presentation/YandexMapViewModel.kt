package bob.colbaskin.hackatontemplate.yandexMap.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class YandexMapViewModel @Inject constructor() : ViewModel() {

    private val _cameraPosition = MutableStateFlow(
        CameraPosition(Point(47.235713, 39.701505), 14f, 0f, 0f) // Ростов-на-Дону
    )
    val cameraPosition: StateFlow<CameraPosition> = _cameraPosition

    fun moveCamera(point: Point, zoom: Float = 14f) {
        _cameraPosition.value = CameraPosition(point, zoom, 0f, 0f)
    }

    fun performAsyncAction() {
        viewModelScope.launch {
            _cameraPosition.value = CameraPosition(Point(47.5, 39.5), 10f, 0f, 0f)
        }
    }
}
