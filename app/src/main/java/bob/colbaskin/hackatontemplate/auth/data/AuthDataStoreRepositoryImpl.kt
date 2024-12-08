package bob.colbaskin.hackatontemplate.auth.data

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import bob.colbaskin.hackatontemplate.auth.domain.local.AuthDataStoreRepository
import bob.colbaskin.hackatontemplate.auth.domain.models.Client
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
        val clientId = intPreferencesKey(name = "client_id")
        val clientName = stringPreferencesKey(name = "client_name")
        val clientBaseUrl = stringPreferencesKey(name = "client_base_url")
        val clientRedirectUrls = stringPreferencesKey(name = "client_redirect_urls")
        val clientType = intPreferencesKey(name = "client_type")
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

    override suspend fun saveClient(client: Client) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.clientId] = client.clientId
            preferences[PreferencesKey.clientName] = client.name
            preferences[PreferencesKey.clientBaseUrl] = client.baseUrl
            preferences[PreferencesKey.clientRedirectUrls] = client.allowedRedirectUrls.joinToString(",")
            preferences[PreferencesKey.clientType] = client.type
        }
    }

    override fun getClient(): Flow<Client?> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val clientId = preferences[PreferencesKey.clientId]
                val name = preferences[PreferencesKey.clientName]
                val baseUrl = preferences[PreferencesKey.clientBaseUrl]
                val redirectUrls = preferences[PreferencesKey.clientRedirectUrls]?.split(",") ?: emptyList()
                val type = preferences[PreferencesKey.clientType]

                if (clientId != null && name != null && baseUrl != null && type != null) {
                    Log.d("Auth", "Client saved in data store: $clientId, $name, $baseUrl, $redirectUrls, $type")
                    Client(clientId, name, baseUrl, redirectUrls, type)
                } else {
                    null
                }
            }
    }

    override suspend fun clearClient() {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.clientId] = -1
            preferences[PreferencesKey.clientName] = ""
            preferences[PreferencesKey.clientBaseUrl] = ""
            preferences[PreferencesKey.clientRedirectUrls] = ""
            preferences[PreferencesKey.clientType] = -1
        }
        Log.d("Auth", "Client cleared from data store")
    }
}