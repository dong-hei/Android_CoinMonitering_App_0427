package com.dk.coinpriceapp.network

import com.dk.coinpriceapp.dataModel.RecentPriceData

data class RecentCoinPriceList (

    val status : String,
    val data : List<RecentPriceData>

    )