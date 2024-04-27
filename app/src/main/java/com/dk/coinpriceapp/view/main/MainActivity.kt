package com.dk.coinpriceapp.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.dk.coinpriceapp.R
import com.dk.coinpriceapp.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Timber.d("onCreate")

        val bottomNaviView = binding.bottomNavigationView
        val navController = findNavController(R.id.fragmentContainerView)

        bottomNaviView.setupWithNavController(navController)
    }
}