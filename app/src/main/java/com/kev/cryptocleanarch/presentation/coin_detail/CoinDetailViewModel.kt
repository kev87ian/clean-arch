package com.kev.cryptocleanarch.presentation.coin_detail

import android.util.Log
import android.util.Log.INFO
import androidx.lifecycle.*
import com.kev.cryptocleanarch.common.Constants
import com.kev.cryptocleanarch.common.Resource
import com.kev.cryptocleanarch.domain.use_case.get_coin.GetSingleCoinUseCase
import com.kev.cryptocleanarch.domain.use_case.get_coins.GetCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.logging.Level.INFO
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
	private val singleCoinsUseCase: GetSingleCoinUseCase,
	savedStateHandle:SavedStateHandle
) : ViewModel() {

	private val _coinState = MutableLiveData<CoinDetailState>()
	val coinState : LiveData<CoinDetailState>
	get() = _coinState


	private val coinId = savedStateHandle.get<String>("coinId")

	private fun getCoinDetails(coinId:String) = viewModelScope.launch {
		singleCoinsUseCase.invoke(coinId).collect{result->
			when(result){
				is Resource.Loading -> {
					_coinState.value = CoinDetailState(isLoading = true)
				}

				is Resource.Error->{
					_coinState.value = CoinDetailState(
						error =result.message?: "An error occured." )
				}
				is Resource.Success->{
					_coinState.value = CoinDetailState(coin = result.data)
				}
			}
		}
	}


	init {
		getCoinDetails(coinId!!)
		Log.i("Jambo", coinId)
	}
}