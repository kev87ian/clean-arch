package com.kev.cryptocleanarch.presentation.coin_detail

import com.kev.cryptocleanarch.domain.model.CoinDetail

data class CoinDetailState(
	val isLoading: Boolean = false,
	val coin: CoinDetail? = null,
	val error: String = ""
)
