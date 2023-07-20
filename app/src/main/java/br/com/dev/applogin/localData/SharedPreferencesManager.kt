package br.com.dev.applogin.localData

import android.content.Context
import androidx.preference.PreferenceManager

class SharedPreferencesManager(context: Context) {

    //variável responsável por receber as funções que compartilha as imformações de cache do android
    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    //funçao que vai armazenar o valor recebido no parametro
    fun saveUserId(userId: String?){
        //captura o valor passado na variável
        sharedPreferences.edit().putString(KEY_USER_ID, userId).apply()
    }

    //funçao que vai pega o valor recebido no parametro
    fun getUserId(): String? {
        return sharedPreferences.getString(KEY_USER_ID, null)
    }

    //limpa o chache com os valores armazenados
    fun clearUserId() {
        sharedPreferences.edit().remove(KEY_USER_ID).apply()
    }

    //Todas as funcoes precisam de uma string como chave
    companion object {
        private const val KEY_USER_ID = "user_id"
    }
}