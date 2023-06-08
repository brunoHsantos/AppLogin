package br.com.dev.applogin.localData

import android.content.Context
import androidx.preference.PreferenceManager

class SharedPreferencesManager(context: Context) {

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun saveUserId(userId: String?){
        sharedPreferences.edit().putString(KEY_USER_ID, userId).apply()
    }


    fun getUserId(): String? {
        return sharedPreferences.getString(KEY_USER_ID, null)
    }

    fun clearUserId() {
        sharedPreferences.edit().remove(KEY_USER_ID).apply()
    }

    companion object {
        private const val KEY_USER_ID = "user_id"
    }
}