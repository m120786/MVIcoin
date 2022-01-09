package com.vb.mvicoin.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vb.mvicoin.navigation.Screens

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Screens.CoinListView.route) {
        composable(Screens.CoinListView.route) {
           CoinListView(navController)
        }
        composable(Screens.CoinDetailView.route + "/{coinId}",
        arguments = listOf(navArgument("coinId") {
            type = NavType.StringType
            defaultValue = "Bitcoin"
            nullable = true })) {
                CoinDetailView(it.arguments?.getString("coinId"))
        }
    }
}