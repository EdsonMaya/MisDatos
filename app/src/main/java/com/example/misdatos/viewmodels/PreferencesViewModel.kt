package com.example.misdatos.viewmodels

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class PreferencesViewModel (val context:Context) {

    companion object{
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
        val NAME = stringPreferencesKey("name")
        val AGE = intPreferencesKey("age")
    }

        ///Equivale al getAge() pero asincrono
        val age: Flow<Int> = context.dataStore.data.map{
                preferences ->
                //No type safety.
                preferences[AGE] ?:0
    }

    ///Equivale al getName() pero asincrono
    val name:Flow<String> = context.dataStore.data.map {
        preferencias->
        preferencias[NAME]?:""
    }

    ///Equivale al setName() y getName()
    suspend fun setNameAndAge(name:String, age:Int){
        context.dataStore.edit{ settings ->
            settings[NAME] = name
            settings[AGE] = age
        }
    }
}