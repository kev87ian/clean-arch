package com.kev.cryptocleanarch.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.kev.cryptocleanarch.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
	private lateinit var navController: NavController

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		val navHostFragment = supportFragmentManager.findFragmentById(R.id.cocktailsNavHostFragment) as NavHostFragment
		navController = navHostFragment.navController


	}


}