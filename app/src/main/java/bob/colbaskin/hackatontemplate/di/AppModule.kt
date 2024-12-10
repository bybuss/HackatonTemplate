package bob.colbaskin.hackatontemplate.di

import android.annotation.SuppressLint
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import android.provider.Settings
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCoroutineScope(): CoroutineScope
            = CoroutineScope(Dispatchers.IO)

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context
            = context

    @SuppressLint("HardwareIds")
    @Provides
    @Singleton
    @Named("deviceFingerprint")
    fun provideDeviceFingerprint(
        @ApplicationContext context: Context
    ): String
            = Settings.Secure.getString(
        context.contentResolver, Settings.Secure.ANDROID_ID
    )

}