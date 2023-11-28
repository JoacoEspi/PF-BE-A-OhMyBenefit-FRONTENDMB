package com.example.ohmybenefits.core

import android.content.Context
import android.content.SharedPreferences


class Preferences(val context: Context) {

    private val SHARED_NAME = "OhMyBenefitsUserDb"
    private val SHARED_USER_EMAIL = "useremail"
    private val SHARED_USER_TOKEN = "token"


    private val storage: SharedPreferences = context.getSharedPreferences(SHARED_NAME, 0)


    fun guardarEmail(email: String) {
        storage.edit().putString(SHARED_USER_EMAIL, email).apply()
    }

    fun obtenerEmail(): String {
       return storage.getString(SHARED_USER_EMAIL, "")!!
    }

    fun guardarToken(token: String) {
        storage.edit().putString(SHARED_USER_TOKEN, token).apply()
    }

    fun obtenerToken(): String {
        return storage.getString(SHARED_USER_TOKEN, "")!!
    }

    fun limpiar() {
        storage.edit().clear()
    }
}