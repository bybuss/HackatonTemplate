package bob.colbaskin.hackatontemplate.auth.di

import bob.colbaskin.hackatontemplate.auth.data.AuthRepositoryImpl
import bob.colbaskin.hackatontemplate.auth.domain.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository {
        return AuthRepositoryImpl()
    }
}