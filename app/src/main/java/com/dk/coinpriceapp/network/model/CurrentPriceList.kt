package com.dk.coinpriceapp.network.model

data class CurrentPriceList (

    val status : String,
    val data : Map<String, Any>

)