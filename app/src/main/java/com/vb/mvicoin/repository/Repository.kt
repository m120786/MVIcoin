package com.vb.mvicoin.repository

import com.vb.mvicoin.api.ApiService
import com.vb.mvicoin.model.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class Repository @Inject constructor(private val api: ApiService) {

    suspend fun getCoins(): List<Coin> = api.getCoins().map { list -> list.toCoin() }

    suspend fun getCoinById(coinId: String): CoinDetail = api.getCoinById(coinId).toCoinDetail()

}