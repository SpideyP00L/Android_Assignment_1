package com.example.datastore.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class UserStore(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("userToken")
        private val STUDENT_ID_KEY = stringPreferencesKey("student_id")
        private val EMAIL_KEY = stringPreferencesKey("email_id")
        private val USER_NAME_ID = stringPreferencesKey("user_name_id")
    }

    val getAccessToken: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[STUDENT_ID_KEY] ?: ""
    }

    val getAccessToken1: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[EMAIL_KEY] ?: ""
    }

    val getAccessToken2: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[USER_NAME_ID] ?: ""
    }



    //Save
    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[STUDENT_ID_KEY] = token
        }
    }

    suspend fun saveToken1(token: String) {
        context.dataStore.edit { preferences ->
            preferences[EMAIL_KEY] = token
        }
    }

    suspend fun saveToken2(token: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME_ID] = token
        }
    }

    // Load button
    suspend fun loadUserName(): String {
        return context.dataStore.data.map { preferences ->
            preferences[USER_NAME_ID] ?: ""
        }.first()
    }

    suspend fun loadEmail(): String {
        return context.dataStore.data.map { preferences ->
            preferences[EMAIL_KEY] ?: ""
        }.first()
    }

    suspend fun loadStudentID(): String {
        return context.dataStore.data.map { preferences ->
            preferences[STUDENT_ID_KEY] ?: ""
        }.first()
    }

    //Clear
    suspend fun clearStoredValues() {
        context.dataStore.edit { preferences ->
            preferences.remove(USER_NAME_ID)
            preferences.remove(EMAIL_KEY)
            preferences.remove(STUDENT_ID_KEY)
        }
    }
}