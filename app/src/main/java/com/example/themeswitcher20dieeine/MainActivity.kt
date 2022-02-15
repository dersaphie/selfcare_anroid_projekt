package com.example.themeswitcher20dieeine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.example.themeswitcher20dieeine.databinding.ActivityMainBinding

//1.Basic Layout
//2.New Class


class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private val homeFragment = HomeFragment()
   // private val secondFragment = SecondFragment() add for all the others
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
       //start Theme is set
        setTheme()
        setContentView(binding.root)
        // when clicking btn Theme is changed and Layout new created
        binding.changeThemeButton.setOnClickListener {
            switchTheme()
            recreate()
        }

        //Fragment is added to Container
        supportFragmentManager.beginTransaction()
            //execute animations correct
            .setReorderingAllowed(true)
            //saved to backstack, to use again
            .addToBackStack("backStack")
            //add fragment to container
            .add(R.id.randomfragmentContainer, homeFragment)
            //commit for FragmentManager
            .commit()

     /*
      //replace current fragment with new one
    bin
    binding.animSport.setOnClickListener({
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.randomfragmentContainer,sportFragment)
            .commit()
    })

      //13.secondFragmentButtpn
       binding.*.setOnClickListener({
           supportFragmentManager.beginTransaction()
               .setReorderingAllowed(true)
               .replace(R.id.fragmentContainer,secondFragment)
               .commit()
       })*/
    }
    /**
     * Menu Layout is inflated
     * return: boolean
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }
    /**
     * When a Menu item is selected the belonging fragment is called
     * return: boolean
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.itemMainMenu -> {
                Toast.makeText(this,"link to MM", Toast.LENGTH_LONG).show()
                true
            }
            R.id.itemMeTime -> {
                Toast.makeText(this,"link to MET", Toast.LENGTH_LONG).show()
                true
            }
            R.id.itemNutrition -> {
                Toast.makeText(this,"link to N", Toast.LENGTH_LONG).show()
                true
            }
            R.id.itemSport-> {
                Toast.makeText(this,"link to S", Toast.LENGTH_LONG).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
