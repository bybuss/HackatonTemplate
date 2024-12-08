package bob.colbaskin.hackatontemplate.yandexMap.domain

import bob.colbaskin.hackatontemplate.yandexMap.data.models.GreenFootprint

interface YandexMapRepository {

    suspend fun getGreenFootprints(): List<GreenFootprint>

    suspend fun saveNewGreenFootprint(greenFootprint: GreenFootprint)
}