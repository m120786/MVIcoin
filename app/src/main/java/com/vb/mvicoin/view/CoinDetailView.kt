package com.vb.mvicoin.view

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vb.mvicoin.intent.MainIntents
import com.vb.mvicoin.mainState.CoinDetailState
import com.vb.mvicoin.model.CoinDetail
import com.vb.mvicoin.viewmodel.CoinDetailViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Composable
fun CoinDetailView(coinId: String?) {
    val detailViewModel = hiltViewModel<CoinDetailViewModel>()
    val detailState = detailViewModel.coinDetailState.collectAsState()
    val coinDetails = remember{ mutableStateOf(CoinDetail("","","","",1,"","",""))}
    val scope = rememberCoroutineScope()

    when (detailState.value) {
        is CoinDetailState.Idle -> {
            OutlinedButton(
                onClick = {
                    scope.launch {
                        detailViewModel.userIntents.emit(MainIntents.GetCoinDetails(coinId!!))
                    }
                }, modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
            ) {
                Text("Get coin ${coinId}")
            }
        }
        is CoinDetailState.Loading -> {}
        is CoinDetailState.Error -> {}
        is CoinDetailState.Coin -> {
            coinDetails.value = (detailState.value as CoinDetailState.Coin).coinDetail
            Row () {
                Text("${coinDetails.value.id}")
                Text("${coinDetails.value.description}")
            }

        }
    }




}