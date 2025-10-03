package com.indigo.mysociety.dataStores

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.indigo.mysociety.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class DataStorePrefs(context: Context) : IDataStorePreferenceAPI {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        context.getString(
            R.string.app_name
        )
    )

    private val dataStore: DataStore<Preferences> = context.dataStore


    override suspend fun <T> getPreference(
        key: Preferences.Key<T>,
        defaultValue: T
    ): Flow<T> {
       return dataStore.data.catch { exception->
            if (exception is IOException){
                emit(emptyPreferences())
            }else{
                throw exception
            }
        }.map{ preferences ->
            preferences[key] ?: defaultValue
        }

    }

    override suspend fun <T> putPreference(
        key: Preferences.Key<T>,
        value: T
    ) {
        dataStore.edit { preferences ->
            preferences[key]=value
        }
    }

    fun readToken(key: Preferences.Key<String>): Flow<String>{
        return dataStore.data.map {
            it[key]?:""
        }
    }

    override suspend fun <T> removePreference(key: Preferences.Key<T>) {
       dataStore.edit { preferences ->
           preferences.remove(key)
       }
    }

    override suspend fun clearAllPreference() {
       dataStore.edit { preferences ->
           preferences.clear()
       }
    }
}