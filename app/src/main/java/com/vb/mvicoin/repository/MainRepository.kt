package com.vb.mvicoin.repository

import com.vb.mvicoin.api.ApiService
import com.vb.mvicoin.model.CoinDTO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class MainRepository @Inject constructor(private val api: ApiService) {

    suspend fun getCoins(): List<CoinDTO> = api.getCoins()
    }
