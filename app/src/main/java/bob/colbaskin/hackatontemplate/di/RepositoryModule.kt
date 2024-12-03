package bob.colbaskin.hackatontemplate.di

import android.content.Context
import bob.colbaskin.hackatontemplate.auth.data.AuthRepositoryImpl
import bob.colbaskin.hackatontemplate.auth.domain.AuthRepository
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
object RepositoryModule {

    @Provides
    @Singleton
    fun provideOnBoardingDataStoreRepository(
        @ApplicationContext context: Context
    ): OnBoardingDataStoreRepository {
        return OnBoardingDataStoreRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository {
        return AuthRepositoryImpl()
    }
}