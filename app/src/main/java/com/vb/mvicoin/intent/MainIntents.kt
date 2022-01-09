package com.vb.mvicoin.intent

sealed class MainIntents {
    object FetchCoins: MainIntents()
    data class FindCoinByName(val name: String): MainIntents()
    data class GetCoinDetails(val id: String): MainIntents()

}