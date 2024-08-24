package org.nmvasani.tictactoe.viewmodels

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SettingViewModel : ViewModel() {
    private var _isPause = MutableStateFlow(true)
    var isPause = _isPause.asStateFlow()

    private var _player1Name = MutableStateFlow("Nimesh")
    var player1Name = _player1Name.asStateFlow()

    private var _player2Name = MutableStateFlow("John")
    var player2Name = _player2Name.asStateFlow()


    fun setPause(value: Boolean) {
        _isPause.value = value
    }
    fun setPlayer1Name(value: String) {
        _player1Name.value = value
    }
    fun setPlayer2Name(value: String) {
        _player2Name.value = value
    }


    fun loadSettings(prefs: DataStore<Preferences>) {
        viewModelScope.launch {
            prefs.data.map { it[booleanPreferencesKey("isPause")] ?: false }.collectLatest {
                println(it)
                _isPause.value = it
            }
        }
        viewModelScope .launch {
            prefs.data.map { it[stringPreferencesKey("player1Name")] ?: "Nimesh" }.collectLatest {
                println(it)
                _player1Name.value = it
            }
        }
        viewModelScope.launch {
            prefs.data.map { it[stringPreferencesKey("player2Name")] ?: "John" }.collectLatest {
                println(it)
                _player2Name.value = it
            }
        }
    }

    fun saveSettings(prefs: DataStore<Preferences>) {
        viewModelScope.launch {
            prefs.edit { dataStore ->
                dataStore[booleanPreferencesKey("isPause")] = isPause.value
            }
            prefs.edit { dataStore ->
                dataStore[stringPreferencesKey("player1Name")] = player1Name.value
            }
            prefs.edit { dataStore ->
                dataStore[stringPreferencesKey("player2Name")] = player2Name.value
            }
        }
    }

}