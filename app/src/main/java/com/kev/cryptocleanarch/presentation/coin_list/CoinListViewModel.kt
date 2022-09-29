package com.kev.cryptocleanarch.presentation.coin_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kev.cryptocleanarch.common.Resource
import com.kev.cryptocleanarch.domain.use_case.get_coins.GetCoinsUseCase
import com.kev.cryptocleanarch.presentation.coin_list.CoinListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
	private val getCoinsUseCase: GetCoinsUseCase
) : ViewModel() {

	/*we create a private mutable live data state that is only accessible by the viewmodel, and a public one that we'll access in our views to ensure we follow the
	principles of clean architecture*/
	private val _coinState = MutableLiveData<CoinListState>()
	val coinState: LiveData<CoinListState>
		get() = _coinState


	private fun getCoins() = viewModelScope.launch {
		getCoinsUseCase().collect{result->
			when (result) {
				is Resource.Success -> {
					_coinState.value = CoinListState(coins = result.data?: emptyList())

				}
				is Resource.Loading -> {
					_coinState.value = CoinListState(isLoading = true)

				}

				is Resource.Error -> {
					_coinState.value = CoinListState(
						error = result.message ?: "An unexpected error occurred,"
					)
				}
			}
		}

	}

	init {
		getCoins()
	}

}