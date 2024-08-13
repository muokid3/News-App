package com.dm.berxley.newsapp.data.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.dm.berxley.newsapp.domain.manager.LocalUserManager
import com.dm.berxley.newsapp.util.Constants.NEWS_APP_ENTRY_VIEWED
import com.dm.berxley.newsapp.util.Constants.USER_SETTINGS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalUserManagerImpl(
    private val context: Context
) : LocalUserManager {
    override suspend fun saveAppEntry() {
        context.dataStore.edit { pref ->
            pref[PreferencesKeys.WELCOME_SCREEN_VIEWED_ENTRY] = true
        }
    }

    override fun readAppEntry(): Flow<Boolean> {
        return context.dataStore.data.map { pref ->
            pref[PreferencesKeys.WELCOME_SCREEN_VIEWED_ENTRY] ?: false
        }
    }
}

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_SETTINGS)

private object PreferencesKeys {
    val WELCOME_SCREEN_VIEWED_ENTRY = booleanPreferencesKey(name = NEWS_APP_ENTRY_VIEWED)
}