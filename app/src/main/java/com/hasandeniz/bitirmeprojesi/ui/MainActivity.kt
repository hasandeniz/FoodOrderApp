package com.hasandeniz.bitirmeprojesi.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.hasandeniz.bitirmeprojesi.R
import com.hasandeniz.bitirmeprojesi.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),BottomNavVisibilityManager {
    private lateinit var binding : ActivityMainBinding
    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        navController = Navigation.findNavController(this, R.id.fragmentContainerView)
        setupWithNavController(binding.bottomNav,navController)

    }

    override fun makeGone() {
        binding.bottomNav.visibility = View.GONE
    }

    override fun makeVisible() {
        binding.bottomNav.visibility = View.VISIBLE

    }

    override fun makeInvisible() {
        binding.bottomNav.visibility = View.INVISIBLE

    }

}