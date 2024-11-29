package bob.colbaskin.hackatontemplate.onBoarding.di

import android.content.Context
import bob.colbaskin.hackatontemplate.onBoarding.data.OnBoardingDataStoreRepositoryImpl
import bob.colbaskin.hackatontemplate.onBoarding.domain.OnBoardingDataStoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OnBoardingModule {

    @Provides
    @Singleton
    fun provideOnBoardingDataStoreRepository(
        @ApplicationContext context: Context
    ): OnBoardingDataStoreRepository {
        return OnBoardingDataStoreRepositoryImpl(context)
    }
}