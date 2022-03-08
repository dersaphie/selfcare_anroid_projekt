package com.example.myroutine


import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.res.Resources
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myroutine.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * Main Activity contains Background Animation, Theme Spinner, Host Fragment and
 * Bottom Navigation. All of them are initialized and called Main.
 */

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var frameAnimation: AnimationDrawable
    private lateinit var appBarConfiguration: AppBarConfiguration

    // Here we set the theme for the activity
    // Note `Utils.onActivityCreateSetTheme` must be called before `setContentView`

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // MUST BE SET BEFORE setContentView
        Utils.onActivityCreateSetTheme(this)
        //ENDE THEME CHANGER

        //define binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // AFTER SETTING THEME
        //setContentView(R.layout.activity_main)
        setupSpinnerItemSelection()

        //val toolbar = findViewById<Toolbar>(R.id.toolbar)
        //setSupportActionBar(toolbar)
        // Set up Action Bar

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment
        ) as NavHostFragment
        navController = navHostFragment.navController

        // TODO: check if this can be removed
        // Setup the bottom navigation view with navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav_menu)
        bottomNavigationView.setupWithNavController(navController)

        // Setup the ActionBar with navController and 3 top level destinations
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment, R.id.sportFragment,  R.id.nutritionFragment)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

/**on start set animation for background imageView, define as Animation and start*/
    override fun onStart() {
        super.onStart()
        binding.animBackgroundImages.setBackgroundResource(R.drawable.animation_list_background)
        frameAnimation = binding.animBackgroundImages.background as AnimationDrawable
        frameAnimation.start()
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }

    private fun setupSpinnerItemSelection() {
        val spThemes = findViewById<Spinner>(R.id.spThemes)
        spThemes.setSelection(ThemeApplication.currentPosition)
        ThemeApplication.currentPosition = spThemes.selectedItemPosition
        spThemes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View,
                position: Int, id: Long
            ) {
                if (ThemeApplication.currentPosition != position) {
                    Utils.changeToTheme(this@MainActivity, position)
                }
                ThemeApplication.currentPosition = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
}