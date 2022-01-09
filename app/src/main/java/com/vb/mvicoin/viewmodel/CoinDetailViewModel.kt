package com.vb.mvicoin.viewmodel

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vb.mvicoin.intent.MainIntents
import com.vb.mvicoin.mainState.CoinDetailState
import com.vb.mvicoin.model.CoinDetail
import com.vb.mvicoin.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(val repository: Repository): ViewModel() {
    val userIntents = MutableSharedFlow<MainIntents>()
    val coinDetailState = MutableStateFlow<CoinDetailState>(CoinDetailState.Idle)
//    val coinDetail = MutableStateFlow<CoinDetail>(CoinDetail("","","","",1,"","",""))

    init {
        getUserIntent()
    }

    private fun getUserIntent() {
        viewModelScope.launch {
            userIntents.collect {
                when (it) {
                    is MainIntents.GetCoinDetails -> getCoinDetails(it.id)
                }
            }
        }
    }

    private fun getCoinDetails(id: String) {
        viewModelScope.launch {
//            coinDetailState.value = CoinDetailState.Loading
            coinDetailState.value = try {
                CoinDetailState.Coin(repository.getCoinById(id))
            } catch(e: Exception) {
                CoinDetailState.Error(e.toString())
            }
        }
    }
}