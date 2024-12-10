package bob.colbaskin.hackatontemplate.di

import bob.colbaskin.hackatontemplate.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    @Singleton
    @Named("apiUrl")
    fun provideApiUrl(): String {
        return "${BuildConfig.BASE_API_URL}/api/"
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        //TODO: Добавить датастор с токеном для передачи в хедер
        @Named("deviceFingerprint") deviceFingerprint: String
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                // FIXME: val token = runBlocking { dataStoreRepository.getToken().first() }
                // FIXME: Log.d("AuthViewModel", "token used in provideOkHttpClient: $token")
                val request = chain.request().newBuilder()
                    // FIXME: .addHeader("Authorization", token)
                    .addHeader("fingerprint", deviceFingerprint)
                    .build()
                chain.proceed(request)
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        @Named("apiUrl") apiUrl: String,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(apiUrl)
            .client(okHttpClient)
            .build()
    }
}