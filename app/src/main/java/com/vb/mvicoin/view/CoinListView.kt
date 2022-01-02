package com.vb.mvicoin.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vb.mvicoin.intent.MainIntent
import kotlinx.coroutines.flow.collect
import com.vb.mvicoin.mainState.MainState
import com.vb.mvicoin.model.CoinDTO
import com.vb.mvicoin.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun CoinListView() {

    val viewModel = hiltViewModel<MainViewModel>()
    var scope = rememberCoroutineScope()
    var coins by remember { mutableStateOf(emptyList<CoinDTO>()) }

    OutlinedButton(onClick = {
        scope.launch {
            viewModel.userIntent.send(MainIntent.FetchCoins)
            viewModel.state.collect {
                when (it) {
                    is MainState.Idle -> {

                    }
                    is MainState.Loading -> {

                    }

                    is MainState.Coins -> {
                        coins = it.coins
                    }
                }

            }
        }
    }, modifier = Modifier.padding(5.dp)) {
        Text("Get coins")
    }

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(coins) { coin ->
            Text("${coin.id}")
        }
    }
}

//    fun observeViewModel() {
//        scope.launch {
//
//        }
//    }
