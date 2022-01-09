package com.vb.mvicoin.mainState

import com.vb.mvicoin.model.CoinDetail

sealed class CoinDetailState {
    object Idle: CoinDetailState()
    object Loading: CoinDetailState()
    data class Coin(val coinDetail: CoinDetail): CoinDetailState()
    data class Error(val error: String): CoinDetailState()
}