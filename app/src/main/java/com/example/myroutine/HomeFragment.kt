package com.example.myroutine

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController

/**
 * functionallity of buttons is definded in Home Fragment
 */

class HomeFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //set on click Listener: Button Sport leads to Sport Fragment
        view.findViewById<Button>(R.id.btnSport)?.setOnClickListener {
            val navOptions: NavOptions = NavOptions.Builder()
                .setPopUpTo(R.id.home, inclusive = false, saveState = true)
                .build()
            findNavController().navigate(HomeFragmentDirections.actionHomeToSport(), navOptions)
        }

        //set on click Listener: Button Sport leads to Sport Fragment
        view.findViewById<Button>(R.id.btnNutrition)?.setOnClickListener {
            val navOptions: NavOptions = NavOptions.Builder()
                .setPopUpTo(R.id.home, inclusive = false, saveState = true)
                .build()
            findNavController().navigate(HomeFragmentDirections.actionHomeToNutrition(), navOptions)
        }

        //set on click Listener: Button Sport leads to Sport Fragment
      /*  view.findViewById<Button>(R.id.btnProfile)?.setOnClickListener {
            val navOptions: NavOptions = NavOptions.Builder()
                .setPopUpTo(R.id.home, inclusive = false, saveState = true)
                .build()
            findNavController().navigate(HomeFragmentDirections.actionHomeToProfile(), navOptions)
        }*/


    }

}