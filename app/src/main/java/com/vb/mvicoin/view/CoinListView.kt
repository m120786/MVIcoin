package com.vb.mvicoin.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.vb.mvicoin.intent.MainIntents
import com.vb.mvicoin.mainState.CoinListState
import com.vb.mvicoin.model.Coin
import com.vb.mvicoin.navigation.Screens
import com.vb.mvicoin.viewmodel.CoinDetailViewModel
import com.vb.mvicoin.viewmodel.CoinListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun CoinListView(navController: NavController) {

    val viewModel = hiltViewModel<CoinListViewModel>()
    val detailViewModel = hiltViewModel<CoinDetailViewModel>()

    var scope = rememberCoroutineScope()
    var coins by remember { mutableStateOf(emptyList<Coin>()) }
    val state = viewModel.state.collectAsState()

    when (state.value) {
        is CoinListState.Idle -> {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                OutlinedButton(
                    onClick = {
                        scope.launch {
                            viewModel.userIntents.emit(MainIntents.FetchCoins)
                        }
                    }, modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                ) {
                    Text("Get coins")
                }

            }
        }
        is CoinListState.Loading -> {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is CoinListState.Coins -> {
            coins = (state.value as CoinListState.Coins).coins
            Column() {
                var coinEditText by rememberSaveable { mutableStateOf("") }
                TextField(
                    value = coinEditText,
                    onValueChange = {
                        coinEditText = it
                    },
                    label = { Text("Coin Name") }
                )
                Button(
                    onClick = {
                        scope.launch {
                            viewModel.userIntents.emit(MainIntents.FindCoinByName(coinEditText))
                        }
                    }
                ) {
                    Text("Find Coin rank")
                }
                ListOfCoins(coins, navController, scope, detailViewModel)
            }

        }
        is CoinListState.FoundCoins -> {
            coins = (state.value as CoinListState.FoundCoins).coin
            ListOfCoins(coins, navController, scope, detailViewModel)
        }

    }
}

@Composable
private fun ListOfCoins(
    coins: List<Coin>,
    navController: NavController,
    scope: CoroutineScope,
    detailViewModel: CoinDetailViewModel
) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(coins) { coin ->
            Row(modifier = Modifier.clickable(onClick = {
                detailViewModel.userIntents.tryEmit(MainIntents.GetCoinDetails(coin.id))
                navController.navigate(Screens.CoinDetailView.route + "/${coin.id}")
            })) {
                Text("${coin.rank}", modifier = Modifier.padding(5.dp))
                Text("${coin.id}", modifier = Modifier.padding(5.dp))
                Text("${coin.name}", modifier = Modifier.padding(5.dp))
            }
        }
    }
}