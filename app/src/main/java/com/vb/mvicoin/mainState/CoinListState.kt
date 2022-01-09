package com.vb.mvicoin.mainState

import com.vb.mvicoin.model.Coin


sealed class CoinListState {
    object Idle: CoinListState()
    object Loading: CoinListState()

    data class Coins(val coins: List<Coin>): CoinListState()
    data class Error(val error: String): CoinListState()

    data class FoundCoins(val coin: List<Coin>): CoinListState()
}