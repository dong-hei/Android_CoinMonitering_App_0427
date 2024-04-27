package com.dk.coinpriceapp.background

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dk.coinpriceapp.db.entity.SelectedCoinPriceEntity
import com.dk.coinpriceapp.network.RecentCoinPriceList
import com.dk.coinpriceapp.repository.DBRepository
import com.dk.coinpriceapp.repository.NetWorkRepository
import timber.log.Timber
import java.util.Calendar
import java.util.Date

/**
 * 관심 있는 코인 리스트를 가져오기 ->
 * API로 각각의 가격변동 정보를 가져오기 ->
 * 코인 각각의 가격변동 정보를 DB에 저장
 */
class GetCoinPriceWorkManager(val context: Context, workerParameters: WorkerParameters) : CoroutineWorker(context, workerParameters) {

    private val dbRepo = DBRepository()
    private val networkRepo = NetWorkRepository()

    override suspend fun doWork(): Result {
        getAllInterestedSelectedCoinData()

        return Result.success()
    }

    // 관심있는 코인 리스트 가져오기
    suspend fun getAllInterestedSelectedCoinData(){

        val selectedCoinList = dbRepo.getAllInterestSelectedCoinData()
        val timeStamp = Calendar.getInstance().time
        for (data in selectedCoinList) {

            Timber.d(data.toString())

            //API로 각각의 가격변동 정보를 가져오기
            val recentCoinPriceList = networkRepo.getInterestCoinPriceData(data.coin_name)

            Timber.d(recentCoinPriceList.toString())

            saveSelectedCoinPrice(
                data.coin_name,
                recentCoinPriceList,
                timeStamp
            )

        }
    }

    fun saveSelectedCoinPrice(
        coinName: String,
        recentCoinPriceList : RecentCoinPriceList,
        timeStamp : Date
    ){
        val selectedCoinPriceEntity = SelectedCoinPriceEntity(
            0,
            coinName,
            recentCoinPriceList.data[0].transaction_date,
            recentCoinPriceList.data[0].type,
            recentCoinPriceList.data[0].units_traded,
            recentCoinPriceList.data[0].price,
            recentCoinPriceList.data[0].total,
            timeStamp
        )

        dbRepo.insertCoinPriceData(selectedCoinPriceEntity)
    }
}