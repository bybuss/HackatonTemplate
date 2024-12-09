package bob.colbaskin.hackatontemplate.auth.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import bob.colbaskin.hackatontemplate.auth.domain.local.AuthDataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.tokenDataStore:DataStore<Preferences> by preferencesDataStore(name = "tokenDataStore")

class AuthDataStoreRepositoryImpl @Inject constructor(
    context: Context
): AuthDataStoreRepository {

    private object PreferencesKey {
        val token = stringPreferencesKey(name = "token")
        val codeVerifier = stringPreferencesKey(name = "codeVerifier")
    }
    private val dataStore = context.tokenDataStore

    override suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.token] = token
        }
    }

    override fun getToken(): Flow<String?> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val token = preferences[PreferencesKey.token]
                token
            }
    }

    override suspend fun clearToken() {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.token] = ""
        }
    }

    override suspend fun saveCodeVerifier(codeVerifier: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.codeVerifier] = codeVerifier
        }
    }

    override fun getCodeVerifier(): Flow<String?> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[PreferencesKey.codeVerifier]
            }
    }

    override suspend fun clearCodeVerifier() {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.codeVerifier] = ""
        }
    }
}