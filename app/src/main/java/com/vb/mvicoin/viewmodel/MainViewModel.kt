package com.vb.mvicoin.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vb.mvicoin.intent.MainIntent
import com.vb.mvicoin.mainState.MainState
import com.vb.mvicoin.model.CoinDTO
import com.vb.mvicoin.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MainRepository): ViewModel() {

    val userIntent = Channel<MainIntent>(Channel.UNLIMITED)
    val state = MutableStateFlow<MainState>(MainState.Idle)
    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when(it) {
                    is MainIntent.FetchCoins -> getCoins()
                }
            }
        }
    }

    private fun getCoins() {
        viewModelScope.launch {
            state.value = MainState.Loading
            state.value = try {
                MainState.Coins(repository.getCoins()) }
            catch (e: Exception) {
                MainState.Error(e.localizedMessage)
            }
        }
    }


}