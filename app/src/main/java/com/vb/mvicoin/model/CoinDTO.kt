package com.vb.mvicoin.model

import com.google.gson.annotations.SerializedName

data class CoinDTO(
    val id: String,
    @SerializedName("is_active")
    val isActive: Boolean,
    @SerializedName("is_new")
    val isNew: Boolean,
    val name: String,
    val rank: Int,
    val symbol: String,
    val type: String
)

fun CoinDTO.toCoin(): Coin {
    return Coin (
            id = id,
            name = name,
            rank = rank,
            symbol = symbol,
            type = type
            )
}

