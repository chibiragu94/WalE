package com.chibi.waie.data.preference

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.dataStore by preferencesDataStore(
    name = "WAIE_user_preferences"
)

/**
 *
 * User can store very secure types of data's like KEY etc..
 * This is Generic class ccreated for all types of the DataTypes
 */
class DataStorePreferenceAPIImpl (context: Context) : DataStorePreferenceAPI {

    private val dataSource = context.dataStore

    override suspend fun<T> getPreference(key : Preferences.Key<T>, defaultValue : T) :
            Flow<T> = dataSource.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences ->
        val result = preferences[key]?: defaultValue
        result
    }


    override suspend fun<T> putPreference(key: Preferences.Key<T>, value: T) {
        dataSource.edit { preferences ->
            preferences[key] = value
        }
    }

    override suspend fun<T> removePreference(key: Preferences.Key<T>) {
        dataSource.edit {
            it.remove(key)
        }
    }

    override suspend fun clearAllPreference() {
        dataSource.edit { preferences ->
            preferences.clear()
        }
    }

}