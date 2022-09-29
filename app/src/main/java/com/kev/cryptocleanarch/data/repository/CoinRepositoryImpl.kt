package com.kev.cryptocleanarch.data.repository

import com.kev.cryptocleanarch.data.remote.CoinPaprikaApi
import com.kev.cryptocleanarch.data.remote.dto.CoinDetailDto
import com.kev.cryptocleanarch.data.remote.dto.CoinDto
import com.kev.cryptocleanarch.domain.repositories.CoinRepository
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
	private val api:CoinPaprikaApi
) : CoinRepository {

	override suspend fun getCoins(): List<CoinDto> {
		return api.getCoins()
	}

	override suspend fun getCoinById(coinId: String): CoinDetailDto {
		return api.getCoinById(coinId)
	}
}