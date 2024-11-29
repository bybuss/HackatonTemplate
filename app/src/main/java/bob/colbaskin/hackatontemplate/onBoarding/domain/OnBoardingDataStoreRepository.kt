package bob.colbaskin.hackatontemplate.onBoarding.domain

import kotlinx.coroutines.flow.Flow

interface OnBoardingDataStoreRepository {

    suspend fun saveOnBoardingState(completed: Boolean)

    fun readOnBoardingState(): Flow<Boolean>
}