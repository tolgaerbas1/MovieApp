package com.whocanfly.movieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.whocanfly.movieapp.views.movie.MainFragment
import timber.log.Timber
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var homeButton: ImageView
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_main)
        homeButton = findViewById(R.id.boyadyo_icon)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        homeButton.setOnClickListener {

        }

        try {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            navController = navHostFragment.navController
        } catch (e: Exception) {
            Timber.e("oops")
            throw e
        }
    }

    override fun onNavigateUp(): Boolean {
        return navController.navigateUp() || super.onNavigateUp()
    }
}