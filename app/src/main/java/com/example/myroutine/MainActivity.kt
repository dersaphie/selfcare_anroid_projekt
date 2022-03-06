package com.example.myroutine


import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.res.Resources
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


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    // Here we set the theme for the activity
    // Note `Utils.onActivityCreateSetTheme` must be called before `setContentView`

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // MUST BE SET BEFORE setContentView
        Utils.onActivityCreateSetTheme(this)

        // AFTER SETTING THEME
        setContentView(R.layout.activity_main)
        setupSpinnerItemSelection()

        //ENDE THEME CHANGER

        //define binding
        //*binding = ActivityMainBinding.inflate(layoutInflater)
        //setContentView(binding.root)*/


        /*val toolbar = findViewById<Toolbar>(R.id.toolbar)
         setSupportActionBar(toolbar)*/
        // Set up Action Bar
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment
        ) as NavHostFragment
        navController = navHostFragment.navController

        /*
        setupBottomNavMenu(navController)
         */
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val dest: String = try {
                resources.getResourceName(destination.id)
            } catch (e: Resources.NotFoundException) {
                destination.id.toString()
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
            setOf(R.id.homeFragment, R.id.sportFragment,  R.id.nutritionFragment)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        ///DB
        //pass context
        val helper = DBHelper(applicationContext)
        //DB
        val db = helper.readableDatabase
        // DB
        // cursor
        @SuppressLint("Recycle")
        val rd = db.rawQuery("SELECT * FROM CARDIO", null)

        //if DB created
        if(rd.moveToNext())
            Toast.makeText(applicationContext,rd.getString(1),Toast.LENGTH_LONG).show()

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }

    fun saveUserDataInDB(cv: ContentValues)
    {
        val helper = DBHelper(applicationContext)
        val db = helper.writableDatabase
        db.insert("USER", null, cv)
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