package bob.colbaskin.hackatontemplate.home.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import bob.colbaskin.hackatontemplate.yandexMap.data.models.GreenFootprint
import bob.colbaskin.hackatontemplate.yandexMap.presentation.YandexMapViewModel
import com.google.android.gms.location.LocationServices

/**
 * @author bybuss
 */
@Composable
fun HomeScreen(
    onClick: () -> Unit,
    //authCode: Int
) {

    val viewModel: YandexMapViewModel = hiltViewModel()
    val placemarks by viewModel.placemarks.collectAsState()
    val context = LocalContext.current
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(
                text = "Home Screen",
                textAlign = TextAlign.Center,
                modifier = Modifier.clickable { onClick() }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (ActivityCompat.checkSelfPermission(
                            context,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            context,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        Toast.makeText(
                            context,
                            "Нет достаточно прав для получения местоположения",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                        if (location != null) {
                            val newFootprint = GreenFootprint(
                                id = (placemarks.size + 1).toString(),
                                name = "Новый Зеленый след",
                                description = "Описание нового действия",
                                latitude = location.latitude,
                                longitude = location.longitude
                            )
                            viewModel.safeNewPlacemark(newFootprint)
                        } else {
                            Toast.makeText(
                                context,
                                "Не удалось получить местоположение",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            ) {
                Text("Добавить Зеленый след")
            }
        }
    }
}
