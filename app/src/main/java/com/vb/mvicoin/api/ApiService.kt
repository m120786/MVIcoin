package com.vb.mvicoin.api

import com.vb.mvicoin.model.CoinDTO
import com.vb.mvicoin.model.CoinDetail
import com.vb.mvicoin.model.CoinDetailDTO
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("v1/coins")
    suspend fun getCoins(): List<CoinDTO>

    @GET("v1/coins/{coinId}")
    suspend fun getCoinById(@Path("coinId") coinId: String): CoinDetailDTO


}