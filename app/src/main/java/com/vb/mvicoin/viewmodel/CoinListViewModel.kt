package com.vb.mvicoin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vb.mvicoin.intent.MainIntents
import com.vb.mvicoin.mainState.CoinListState
import com.vb.mvicoin.model.Coin
import com.vb.mvicoin.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(private val repository: Repository): ViewModel() {

val userIntents = MutableSharedFlow<MainIntents>()
    val state = MutableStateFlow<CoinListState>(CoinListState.Idle)
    val coins = MutableStateFlow<List<Coin>>(emptyList<Coin>())

    init {
        handleIntents()
    }

    private fun handleIntents() {
        viewModelScope.launch {
            userIntents.collect {
                when(it) {
                    is MainIntents.FetchCoins -> getCoins()
                    is MainIntents.FindCoinByName -> getCoin(it.name)
                }

            }
        }
    }

    private fun getCoin(name: String) {
        viewModelScope.launch {
            state.value = try {
                CoinListState.FoundCoins(coins.value.filter { it.name == name })
            } catch (e: Exception) {
                CoinListState.Error(e.localizedMessage)
            }
        }

    }

    private fun getCoins() {
        viewModelScope.launch {
            state.value = CoinListState.Loading
            state.value = try {
                coins.value = repository.getCoins()
                CoinListState.Coins(coins.value)
            }
            catch (e: Exception) {
                CoinListState.Error(e.localizedMessage)
            }
        }
    }


}