package com.dk.coinpriceapp.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dk.coinpriceapp.dataModel.UpDownDataSet
import com.dk.coinpriceapp.db.entity.InterestCoinEntity
import com.dk.coinpriceapp.repository.DBRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    private val dbRepo = DBRepository()
    lateinit var selectedCoinList: LiveData<List<InterestCoinEntity>>
    private val _arr15m = MutableLiveData<List<UpDownDataSet>>()
    val arr15m : LiveData<List<UpDownDataSet>>
        get() = _arr15m
    private val _arr30m = MutableLiveData<List<UpDownDataSet>>()
    val arr30m : LiveData<List<UpDownDataSet>>
        get() = _arr30m
    private val _arr45m = MutableLiveData<List<UpDownDataSet>>()
    val arr45m : LiveData<List<UpDownDataSet>>
        get() = _arr45m

    // CoinList Fragment
    fun getAllInterestCoinData() = viewModelScope.launch {

        val coinList = dbRepo.getAllInterestCoinData().asLiveData()
        selectedCoinList = coinList
    }

    fun updateInterestCoinData(interestCoinEntity: InterestCoinEntity) =
        viewModelScope.launch(Dispatchers.IO) {

            interestCoinEntity.selected = !interestCoinEntity.selected
            dbRepo.updateInterestCoinData(interestCoinEntity)
}
    // PriceChange Fragment
    fun getAllSelectedCoinData() = viewModelScope.launch(Dispatchers.IO) {

        // 관심있다고 선택한 코인리스트를 가져온다. ->
        val selectedCoinList = dbRepo.getAllInterestSelectedCoinData()

        val arr15m = ArrayList<UpDownDataSet>()
        val arr30m = ArrayList<UpDownDataSet>()
        val arr45m = ArrayList<UpDownDataSet>()

        // 루프문으로 가져온다 ->
        for (i in selectedCoinList) {

            // 저장된 코인 가격 리스트를 가져와 시간대마다 변경되었는지를 알려주는 로직 작성
            val coinName = i.coin_name
            val oneCoinData = dbRepo.getOneSelectedCoinData(coinName).reversed()

            val size = oneCoinData.size

            when {
                size > 1 -> {
                    //DB에 값이 2개 이상은 있다. (현재와 15분 가격을 비교하려면 데이터가 2개는 있어야 한다.)
                    val changedPrice = oneCoinData[0].price.toDouble() - oneCoinData[1].price.toDouble()
                    val upDownDataSet = UpDownDataSet(
                        coinName,
                        changedPrice.toString()
                    )
                    arr15m.add(upDownDataSet)
                }
                size > 2 -> {
                    //DB에 값이 3개 이상은 있다. (현재와 30분 가격을 비교하려면 데이터가 3개는 있어야 한다.)
                    val changedPrice = oneCoinData[0].price.toDouble() - oneCoinData[2].price.toDouble()
                    val upDownDataSet = UpDownDataSet(
                        coinName,
                        changedPrice.toString()
                    )
                    arr30m.add(upDownDataSet)

                }
                size > 3 -> {
                    //DB에 값이 4개 이상은 있다. (현재와 45분 가격을 비교하려면 데이터가 4개는 있어야 한다.)
                    val changedPrice = oneCoinData[0].price.toDouble() - oneCoinData[3].price.toDouble()
                    val upDownDataSet = UpDownDataSet(
                        coinName,
                        changedPrice.toString()
                    )
                    arr45m.add(upDownDataSet)

                }
            }

            withContext(Dispatchers.Main) {
                _arr15m.value = arr15m
                _arr30m.value = arr30m
                _arr45m.value = arr45m
            }
        }
    }

}