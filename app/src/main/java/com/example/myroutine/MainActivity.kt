package com.example.myroutine

import android.content.res.Resources
import android.graphics.drawable.Animatable
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.example.myroutine.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

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
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        // Set up Action Bar
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment
        ) as NavHostFragment
        navController = navHostFragment.navController

        // when clicking btn Theme is changed and Layout new created
        binding.changeThemeButton.setOnClickListener {
            switchTheme()
            recreate()
        }



        /*
        setupBottomNavMenu(navController)
         */
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

        // Setup the bottom navigation view with navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav_menu)
        bottomNavigationView.setupWithNavController(navController)

        // Setup the ActionBar with navController and 3 top level destinations
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.home, R.id.sport,  R.id.nutrition)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)


        ///DB
        //pass context
        var helper = DBHelper(applicationContext)
        //DB
        var db = helper.readableDatabase
        //cursor
        var rd = db.rawQuery("SELECT * FROM CARDIO", null)

        //if DB created
        if(rd.moveToNext())
            Toast.makeText(applicationContext,rd.getString(1),Toast.LENGTH_LONG).show()

    }

    override fun onStart() {
       /* super.onStart()


        val rocketImage: ImageView = findViewById(R.id.picAPIGoodMood)
        rocketImage.setBackgroundResource(R.drawable.animation_list_background)

        val rocketAnimation = rocketImage.background
        if (rocketAnimation is Animatable) {
            rocketAnimation.start()
        }*/

        super.onStart()
        //
        binding.picAPIGoodMood.setBackgroundResource(R.drawable.animation_list_background)
        frameAnimation = binding.picAPIGoodMood.background as AnimationDrawable
        frameAnimation.start()
    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }



}