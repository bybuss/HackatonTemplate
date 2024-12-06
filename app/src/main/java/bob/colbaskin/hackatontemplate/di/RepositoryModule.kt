package bob.colbaskin.hackatontemplate.di

import android.content.Context
import bob.colbaskin.hackatontemplate.auth.data.AuthRepositoryImpl
import bob.colbaskin.hackatontemplate.auth.data.TokenDataStoreRepositoryImpl
import bob.colbaskin.hackatontemplate.auth.domain.local.TokenDataStoreRepository
import bob.colbaskin.hackatontemplate.auth.domain.network.AuthRepository
import bob.colbaskin.hackatontemplate.auth.domain.network.AuthService
import bob.colbaskin.hackatontemplate.onBoarding.data.OnBoardingDataStoreRepositoryImpl
import bob.colbaskin.hackatontemplate.onBoarding.domain.OnBoardingDataStoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
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
    fun provideAuthRepository(
        authService: AuthService,
        tokenDataStoreRepository: TokenDataStoreRepository
    ): AuthRepository {
        return AuthRepositoryImpl(authService, tokenDataStoreRepository)
    }

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideTokenDataStoreRepository(
        @ApplicationContext context: Context
    ): TokenDataStoreRepository {
        return TokenDataStoreRepositoryImpl(context)
    }
}