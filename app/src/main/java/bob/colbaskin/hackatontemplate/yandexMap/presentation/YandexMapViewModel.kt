package bob.colbaskin.hackatontemplate.yandexMap.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bob.colbaskin.hackatontemplate.yandexMap.data.models.GreenFootprint
import bob.colbaskin.hackatontemplate.yandexMap.domain.YandexMapRepository
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class YandexMapViewModel @Inject constructor(
    private val yandexMapRepository: YandexMapRepository
) : ViewModel() {

    private val _cameraPosition = MutableStateFlow(
        CameraPosition(Point(47.235713, 39.701505), 14f, 0f, 0f)
    )
    val cameraPosition: StateFlow<CameraPosition> = _cameraPosition.asStateFlow()

    private val _placemarks = MutableStateFlow<List<GreenFootprint>>(emptyList())
    val placemarks = _placemarks.asStateFlow()

    private val _selectedPlacemark = MutableStateFlow<GreenFootprint?>(null)
    val selectedPlacemark = _selectedPlacemark.asStateFlow()

    init {
        Log.d("YandexMap", "Initial placemarks: ${_placemarks.value}")
        loadPlacemarks()
    }

    private fun loadPlacemarks() {
        runBlocking {
            _placemarks.value += yandexMapRepository.getGreenFootprints()
            Log.d("YandexMap", "Loading placemarks: ${_placemarks.value}")
        }
    }

    fun updateSelectedPlacemark(placemark: GreenFootprint?) {
        _selectedPlacemark.value = placemark
    }

    // TODO: получаю нормально, но нужно либо бд делать либо хранить на сервере метки пользователя!!
    fun safeNewPlacemark(newPlacemark: GreenFootprint) {
        viewModelScope.launch {
            _placemarks.value += newPlacemark
            yandexMapRepository.saveNewGreenFootprint(newPlacemark)
            Log.d("YandexMap", "Saving placemark: $newPlacemark")
            Log.d("YandexMap", "Placemarks list: ${_placemarks.value}")

        }
    }
}
