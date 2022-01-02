package com.vb.mvicoin.intent

sealed class MainIntent {
    object FetchCoins: MainIntent()
}