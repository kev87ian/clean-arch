package com.kev.cryptocleanarch.domain.use_case.get_coin

import com.kev.cryptocleanarch.common.Resource
import com.kev.cryptocleanarch.data.remote.dto.toCoinDetail
import com.kev.cryptocleanarch.domain.model.Coin
import com.kev.cryptocleanarch.domain.model.CoinDetail
import com.kev.cryptocleanarch.domain.repositories.CoinRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.Dispatcher
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetSingleCoinUseCase @Inject constructor(
	val repository: CoinRepository
){
	operator fun invoke(coinId:String) : Flow<Resource<CoinDetail>> = flow {
		try {

			emit(Resource.Loading())
			val coin = repository.getCoinById(coinId).toCoinDetail()
			emit(Resource.Success(coin))

		}catch (e:HttpException){
			emit(Resource.Error(e.localizedMessage?: "An unexpected error occurred."))
		}catch (e:IOException){
			emit(Resource.Error("Couldn't reach server. Check your internet connection"))
		}
	}.flowOn(Dispatchers.IO)

}