package com.example.myroutine

import android.content.res.Resources
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.example.myroutine.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


/**
 * Main Activity contains Background Animation, Theme Spinner, Host Fragment and
 * Bottom Navigation. All of them are initialized and called Main.
 */

class MainActivity : ThemeChange() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var frameAnimation: AnimationDrawable
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //define binding
        binding = ActivityMainBinding.inflate(layoutInflater)

        //start Theme is set
        setTheme()
        setContentView(binding.root)

        //set toolbar and support
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        //initialize support Fragment Manager and Nav Controller
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment
        ) as NavHostFragment
        navController = navHostFragment.navController

        // Setup the bottom navigation view with navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav_menu)
        bottomNavigationView.setupWithNavController(navController)

        // Setup the ActionBar with navController and 3 top level destinations
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.home, R.id.sport,  R.id.nutrition)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)


        // set on click listener for Theme Change + recration of app
        binding.changeThemeButton.setOnClickListener {
            switchTheme()
            recreate()
        }

        /*
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val dest: String = try {
                resources.getResourceName(destination.id)
            } catch (e: Resources.NotFoundException) {
                Integer.toString(destination.id)
            }

            Toast.makeText(this@MainActivity, "Navigated to $dest",
                Toast.LENGTH_SHORT).show()
            Log.d("NavigationActivity", "Navigated to $dest")
        }
*/




/*
        ///DB
        //pass context
        var helper = DBHelper(applicationContext)
        //DB
        var db = helper.readableDatabase
        //cursor
        var rd = db.rawQuery("SELECT * FROM CARDIO", null)

        //if DB created
        if(rd.moveToNext())
            Toast.makeText(applicationContext,rd.getString(1),Toast.LENGTH_LONG).show()*/

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
}