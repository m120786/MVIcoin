package com.vb.mvicoin.navigation

sealed class Screens(val route: String) {
    object CoinListView: Screens("coin_list_view")
    object CoinDetailView: Screens("coin_detail_view")
}
