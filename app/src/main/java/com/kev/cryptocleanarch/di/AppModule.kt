package com.kev.cryptocleanarch.di

import com.kev.cryptocleanarch.common.Constants
import com.kev.cryptocleanarch.common.Constants.Companion.BASE_URL
import com.kev.cryptocleanarch.data.remote.CoinPaprikaApi
import com.kev.cryptocleanarch.data.repository.CoinRepositoryImpl
import com.kev.cryptocleanarch.domain.repositories.CoinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

	@Singleton
	@Provides
	fun providesLoggingInterceptor()= HttpLoggingInterceptor().apply {
		level = HttpLoggingInterceptor.Level.BODY
	}

	@Singleton
	@Provides
	fun providesOkhttpClient(loggingInterceptor: HttpLoggingInterceptor) : OkHttpClient =
		OkHttpClient.Builder()
			.addInterceptor(loggingInterceptor)
			.build()

	@Singleton
	@Provides
	fun providesRetrofit(okHttpClient: OkHttpClient) : Retrofit{
		return Retrofit.Builder()
			.baseUrl(BASE_URL)
			.addConverterFactory(GsonConverterFactory.create())
			.client(okHttpClient)
			.build()
	}

	@Singleton
	@Provides
	fun providesApiService(retrofit: Retrofit) : CoinPaprikaApi{
		return retrofit.create(CoinPaprikaApi::class.java)
	}


/*	@Provides
	@Singleton
	fun providesPaprikaApiService(): CoinPaprikaApi {
		return Retrofit.Builder()
			.baseUrl(Constants.BASE_URL)
			.addConverterFactory(GsonConverterFactory.create())
			.build()
			.create(CoinPaprikaApi::class.java)
	}*/

	@Provides
	@Singleton
	fun providesCoinRepository(api: CoinPaprikaApi): CoinRepository {
		return CoinRepositoryImpl(api)
	}

}