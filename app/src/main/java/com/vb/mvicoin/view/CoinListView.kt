package com.vb.mvicoin.view

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vb.mvicoin.intent.MainIntent
import com.vb.mvicoin.mainState.MainState
import com.vb.mvicoin.model.CoinDTO
import com.vb.mvicoin.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Composable
fun CoinListView() {

    val viewModel = hiltViewModel<MainViewModel>()
    var scope = rememberCoroutineScope()
    var coins by remember { mutableStateOf(emptyList<CoinDTO>()) }
    val loading = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit, block = {
        viewModel.state.collect {
            when (it) {
                is MainState.Idle -> {

                }
                is MainState.Loading -> {
                    loading.value = true
                }

                is MainState.Coins -> {
                    coins = it.coins
                    loading.value = false
                }
            }

        }
    })
    Column() {
        Row(horizontalArrangement = Arrangement.Center) {
            OutlinedButton(
                onClick = {
                    scope.launch {
                        viewModel.userIntent.send(MainIntent.FetchCoins)

                    }
                }, modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
            ) {
                Text("Get coins")
            }
        }
        if (loading.value) {
            CircularProgressIndicator()
        }
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(coins) { coin ->
                Row() {
                    Text("${coin.rank}", modifier = Modifier.padding(5.dp))
                    Text("${coin.id}", modifier = Modifier.padding(5.dp))
                    Text("${coin.name}", modifier = Modifier.padding(5.dp))
                }
            }
        }
    }
}