package com.example.ohmybenefits.core

import android.content.Context
import android.content.SharedPreferences

class SharedPreferences(val context: Context) {

    private val SHARED_NAME = "myDtb"
    private val SHARED_USER_FAVOURITES_DOGS = "favorite_dogs"

    private val storage: SharedPreferences = context.getSharedPreferences(SHARED_NAME, 0)

    fun addFavoriteProduct(id: String) {
        val set = storage.getStringSet(SHARED_USER_FAVOURITES_DOGS, emptySet())
        val mutableSet = set?.map { it.toString() }?.toMutableSet()
        mutableSet?.add(id)
        storage.edit().putStringSet(SHARED_USER_FAVOURITES_DOGS, mutableSet).apply()
    }

    fun removeFavoriteProduct(id: String) {
        val set = storage.getStringSet(SHARED_USER_FAVOURITES_DOGS, emptySet())
        val mutableSet = set?.map { it.toString() }?.toMutableSet()
        mutableSet?.remove(id)
        storage.edit().putStringSet(SHARED_USER_FAVOURITES_DOGS, mutableSet).apply()
    }

    fun isFavoriteProduct(id: String): Boolean {
        val set = storage.getStringSet(SHARED_USER_FAVOURITES_DOGS, emptySet())
        if (set != null) {
            return set.contains(id)
        }
        return false
    }

    fun getUserFavouriteProducts(): List<String> {
        val set = storage.getStringSet(SHARED_USER_FAVOURITES_DOGS, emptySet())
        if (set != null) {
            return set.map { it }
        }
        return emptyList()
    }

    fun wipe() {
        //borrar los datos de la bd
        storage.edit().clear().apply()
    }
}