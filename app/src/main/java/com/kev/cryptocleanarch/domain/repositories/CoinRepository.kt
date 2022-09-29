package com.kev.cryptocleanarch.domain.repositories

import com.kev.cryptocleanarch.data.remote.dto.CoinDetailDto
import com.kev.cryptocleanarch.data.remote.dto.CoinDto

interface CoinRepository {

	suspend fun getCoins() : List<CoinDto>
	suspend fun getCoinById(coinId:String) : CoinDetailDto
}