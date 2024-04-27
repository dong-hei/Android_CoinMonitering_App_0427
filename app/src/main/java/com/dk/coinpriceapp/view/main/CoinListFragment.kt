package com.dk.coinpriceapp.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dk.coinpriceapp.R
import com.dk.coinpriceapp.databinding.FragmentCoinListBinding
import com.dk.coinpriceapp.db.entity.InterestCoinEntity
import com.dk.coinpriceapp.view.adapter.CoinListRVAdapter
import timber.log.Timber


class CoinListFragment : Fragment() {

    private val vm: MainViewModel by activityViewModels()

    private var _binding: FragmentCoinListBinding? = null
    private val binding get() = _binding!!

    private val selectedList = ArrayList<InterestCoinEntity>()
    private val unSelectedList = ArrayList<InterestCoinEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCoinListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.getAllInterestCoinData()
        vm.selectedCoinList.observe(viewLifecycleOwner) {

            //초기화
            selectedList.clear()
            unSelectedList.clear()

            for (i in it) {
                if (i.selected) {
                    selectedList.add(i)
                } else {
                    unSelectedList.add(i)
                }
            }

            Timber.d(selectedList.toString())
            Timber.d(unSelectedList.toString())

            setSelectedListRV()

        }
    }

    private fun setSelectedListRV() {
        val selectedRVAdapter = CoinListRVAdapter(requireContext(), selectedList)
        binding.selectedCoinRV.adapter = selectedRVAdapter
        binding.selectedCoinRV.layoutManager = LinearLayoutManager(requireContext())

        selectedRVAdapter.itemClick = object : CoinListRVAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {

                vm.updateInterestCoinData(selectedList[position])
            }

        }
        val unselectedRVAdapter = CoinListRVAdapter(requireContext(), unSelectedList)
        binding.unSelectedCoinRV.adapter = unselectedRVAdapter
        binding.unSelectedCoinRV.layoutManager = LinearLayoutManager(requireContext())

        unselectedRVAdapter.itemClick = object : CoinListRVAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {

                vm.updateInterestCoinData(unSelectedList[position])
            }
        }
    }

        override fun onDestroy() {
            super.onDestroy()
            _binding = null
        }

    }