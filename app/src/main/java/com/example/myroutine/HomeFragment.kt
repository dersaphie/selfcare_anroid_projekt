package com.example.myroutine

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions

class HomeFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //anim for side change
        val options = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }

        /*
        *FEHLER!! Das neue Fragment wird zum Home Fragment -> eigentlich sollte Action aufruf es lösen
        * aber absturz bis jetzt
         */
        //where to click an whats comes next //change writing navigate_sport_button
        view.findViewById<Button>(R.id.animSport)?.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_sport, null, options)
        }

        // navigate with action for safe arguments
        /*view.findViewById<Button>(R.id.animSport)?.setOnClickListener {
            //val flowStepNumberArg = 1
            val action = HomeFragmentDirections.actionHomeFragmentToSportFragment()
            findNavController().navigate(action)
        }
        */

        //NAvigate via action and not to page
        view.findViewById<Button>(R.id.animMeTime)?.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_home_to_nutrition, null)
        )

    }

    /*
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
    }
    */
}