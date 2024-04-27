package com.dk.coinpriceapp.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dk.coinpriceapp.R
import com.dk.coinpriceapp.databinding.FragmentPriceChangeBinding
import com.dk.coinpriceapp.view.adapter.PriceListUpDownRVAdapter
import timber.log.Timber

class PriceChangeFragment : Fragment() {

    private val vm : MainViewModel by activityViewModels()
    private var _binding : FragmentPriceChangeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = FragmentPriceChangeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.getAllSelectedCoinData()
        vm.arr15m.observe(viewLifecycleOwner) {
            Timber.tag("Data 15M").d(it.toString())

            val priceListUpdownRVAdapter = PriceListUpDownRVAdapter(requireContext(), it)
            binding.price15m.adapter = priceListUpdownRVAdapter
            binding.price15m.layoutManager = LinearLayoutManager(requireContext())
        }

        vm.arr30m.observe(viewLifecycleOwner) {
            Timber.tag("Data 30M").d(it.toString())

            val priceListUpdownRVAdapter = PriceListUpDownRVAdapter(requireContext(), it)
            binding.price30m.adapter = priceListUpdownRVAdapter
            binding.price30m.layoutManager = LinearLayoutManager(requireContext())
        }

        vm.arr30m.observe(viewLifecycleOwner) {
             Timber.tag("Data 45M").d(it.toString())

            val priceListUpdownRVAdapter = PriceListUpDownRVAdapter(requireContext(), it)
            binding.price45m.adapter = priceListUpdownRVAdapter
            binding.price45m.layoutManager = LinearLayoutManager(requireContext())
         }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}