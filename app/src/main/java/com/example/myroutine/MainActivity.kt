package com.example.myroutine

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
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

    //Bottom funktioniert erweiterung in layout und Layout definiert die

    /*private fun setupBottomNavMenu(navController: NavController) {
        // TODO STEP 9.3 - Use NavigationUI to set up Bottom Nav
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNav?.setupWithNavController(navController)
    }

     */
    /*
    //inflating menu and nav_view which is layout for host fragment
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val retValue = super.onCreateOptionsMenu(menu)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        // The NavigationView already has these same navigation items, so we only add
        // navigation items to the menu here if there isn't a NavigationView
        if (navigationView == null) {
            menuInflater.inflate(R.menu.overflow_menu, menu)
            return true
        }
        return retValue
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController(R.id.nav_host_fragment))

    }
     override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController(R.id.nav_host_fragment))

    }
*/

}