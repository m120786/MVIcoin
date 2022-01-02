package com.vb.mvicoin.mainState

import com.vb.mvicoin.model.CoinDTO
import kotlinx.coroutines.flow.Flow

sealed class MainState {
    object Idle: MainState()
    object Loading: MainState()

    data class Coins(val coins: List<CoinDTO>): MainState()
    data class Error(val error: String): MainState()
}