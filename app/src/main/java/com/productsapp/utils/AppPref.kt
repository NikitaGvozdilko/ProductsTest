package com.productsapp.utils


import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.productsapp.api.model.LoginData

object AppPref {
    private val PREF_TOKEN: String = "token"
    private val PREF_PASSWORD: String = "password"
    private val PREF_LOGIN: String = "login"
    private val TAG = "AppPref"

    private fun getSharedPref(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun saveLoginData(context: Context, loginData: LoginData, token: String) {
        getSharedPref(context).edit()
            .putString(PREF_LOGIN, loginData.username)
            .putString(PREF_PASSWORD, loginData.password)
            .putString(PREF_TOKEN, token)
            .apply()
    }

    fun getLoginData(context: Context): LoginData? {
        val login = getSharedPref(context).getString(PREF_LOGIN, null)
        val password = getSharedPref(context).getString(PREF_PASSWORD, null)
        if(login.isNullOrEmpty() || password.isNullOrEmpty()) return null
        return LoginData(login, password)
    }

    fun getToken(context: Context) = getSharedPref(context).getString(PREF_TOKEN, null)

}//no instance