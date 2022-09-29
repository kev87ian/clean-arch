package com.kev.cryptocleanarch.domain.use_case.get_coins

import com.kev.cryptocleanarch.common.Resource
import com.kev.cryptocleanarch.data.remote.dto.toCoin
import com.kev.cryptocleanarch.domain.model.Coin
import com.kev.cryptocleanarch.domain.repositories.CoinRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
	private val repository: CoinRepository
) {
	//a use case should only have one public function


	operator fun invoke(): Flow<Resource<List<Coin>>> = flow {
		try {
			emit(Resource.Loading())
			val coins = repository.getCoins().map{ it.toCoin()}
			emit(Resource.Success(coins))

		} catch (e: HttpException) {
			emit(Resource.Error(e.localizedMessage?: "An unexpected error occurred." ))
		} catch (e: IOException) {
			emit(Resource.Error("Couldn't reach server. Check your internet connection"))
		}
	}.flowOn(Dispatchers.IO)

}