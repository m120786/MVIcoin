package com.vb.mvicoin.api

import com.vb.mvicoin.model.CoinDTO
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface ApiService {

    @GET("v1/coins")
    suspend fun getCoins(): List<CoinDTO>
}