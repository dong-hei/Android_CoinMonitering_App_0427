package com.dk.coinpriceapp.dataModel

import com.dk.coinpriceapp.dataModel.CurrentPrice

data class CurrentPriceResult (
    val coinName : String,
    val coinInfo : CurrentPrice
)