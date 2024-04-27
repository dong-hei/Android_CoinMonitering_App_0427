package com.dk.coinpriceapp.dataModel

data class RecentPriceData(
    val price: String,
    val total: String,
    val transaction_date: String,
    val type: String,
    val units_traded: String
)