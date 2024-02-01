package com.example.datastore.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserStore(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("userToken")
        private val USER_TOKEN_KEY = stringPreferencesKey("user_token")
        private val USER_TOKEN_KEY_ID = stringPreferencesKey("user_token_id")
        private val USER_TOKEN_KEY_Name = stringPreferencesKey("user_token_name")
    }

    val getAccessToken: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[USER_TOKEN_KEY] ?: ""
    }

    val getAccessToken1: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[USER_TOKEN_KEY_ID] ?: ""
    }

    val getAccessToken2: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[USER_TOKEN_KEY_Name] ?: ""
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_TOKEN_KEY] = token
        }
    }

    suspend fun saveToken1(token: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_TOKEN_KEY_ID] = token
        }
    }

    suspend fun saveToken2(token: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_TOKEN_KEY_Name] = token
        }
    }
}