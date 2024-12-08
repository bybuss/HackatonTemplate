package bob.colbaskin.hackatontemplate.yandexMap.data

import android.util.Log
import bob.colbaskin.hackatontemplate.yandexMap.data.models.GreenFootprint
import bob.colbaskin.hackatontemplate.yandexMap.domain.YandexMapRepository

class YandexMapRepositoryImpl: YandexMapRepository {

    override suspend fun getGreenFootprints(): List<GreenFootprint> {
        return listOf(
            GreenFootprint(
                id = "1",
                name = "Зеленый след 1",
                description = "Это описание действия, оставившего след.",
                latitude = 47.235713,
                longitude = 39.701505
            ),
            GreenFootprint(
                id = "2",
                name = "Зеленый след 2",
                description = "Описание другого действия.",
                latitude = 47.235772,
                longitude = 39.701574
            )
        )
    }

    override suspend fun saveNewGreenFootprint(greenFootprint: GreenFootprint) {
        Log.d("YandexMap", "saved new green footprint: $greenFootprint")
    }
}