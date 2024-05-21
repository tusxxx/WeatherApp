package com.example.weatherapp

import android.os.Bundle
import androidx.core.net.toUri
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.fragment.NavHostFragment
import com.example.weatherapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupListeners()
    }

    private fun setupListeners() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fcvMainContainer) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bnvMain.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home_item -> {
                    navController.navigate("android-app://com.example.weather_app/home".toUri())
                    binding.tvTitle.text = getString(R.string.home)
                    true
                }

                R.id.favorites_item -> {
                    binding.tvTitle.text = getString(R.string.favorites)
                    navController.navigate("android-app://com.example.weather_app/favorites".toUri())
                    true
                }

                R.id.settings_item -> {
                    binding.tvTitle.text = getString(R.string.settings)
                    navController.navigate("android-app://com.example.weather_app/settings".toUri())
                    true
                }

                else -> false
            }
        }
    }
}