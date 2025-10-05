package com.indigo.mysociety.dataStores

import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceKeys {
    val KEY_AUTH_TOKEN = stringPreferencesKey("key_auth")
    val KEY_USER_INFO = stringPreferencesKey("key_user_info")
}