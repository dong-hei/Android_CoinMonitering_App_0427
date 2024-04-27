package com.dk.coinpriceapp.network

import com.dk.coinpriceapp.network.model.CurrentPriceList
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    // public/ticker/ALL_KRW
    @GET("public/ticker/ALL_KRW")
    suspend fun getCurrentCoinList(): CurrentPriceList

    // https://api.bithumb.com/public/transaction_history/BTC_KRW
    @GET("public/transaction_history/{coin}_KRW")
    suspend fun getRecentCoinPrice(@Path("coin") coin : String) : RecentCoinPriceList

}