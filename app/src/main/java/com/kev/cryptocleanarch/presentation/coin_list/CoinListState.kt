package com.kev.cryptocleanarch.presentation.coin_list

import com.kev.cryptocleanarch.domain.model.Coin

data class CoinListState(
	val isLoading: Boolean = false,
	val coins : List<Coin> = emptyList(),
	val error : String = ""
)
