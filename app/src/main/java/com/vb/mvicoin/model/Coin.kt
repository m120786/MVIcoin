package com.vb.mvicoin.model

import com.google.gson.annotations.SerializedName

data class Coin(
    val id: String,
    val name: String,
    val rank: Int,
    val symbol: String,
    val type: String
)