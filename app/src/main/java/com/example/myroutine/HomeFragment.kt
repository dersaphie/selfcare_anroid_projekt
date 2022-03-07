package com.example.myroutine

import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
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
        view.findViewById<Button>(R.id.btnSport)?.setOnClickListener {
            val navOptions: NavOptions = NavOptions.Builder()
                .setPopUpTo(R.id.homeFragment, inclusive = false, saveState = true)
                //.setRestoreState(restoreState = true)
                .build()
            findNavController().navigate(HomeFragmentDirections.actionHomeToSport(), navOptions)
            //findNavController().navigate(R.id.action_home_to_sport, null, navOptions)
        }
        view.findViewById<Button>(R.id.btnNutrition)?.setOnClickListener {
            val navOptions: NavOptions = NavOptions.Builder()
                .setPopUpTo(R.id.homeFragment, inclusive = false, saveState = true)
                //.setRestoreState(restoreState = true)
                .build()
            findNavController().navigate(HomeFragmentDirections.actionHomeToNutrition(), navOptions)
            //findNavController().navigate(R.id.action_home_to_sport, null, navOptions)
        }
        /*
        .setEnterAnim(R.anim.slide_in_right)
                .setExitAnim(R.anim.slide_out_left)
                .setPopEnterAnim(R.anim.slide_in_left)
                .setPopExitAnim(R.anim.slide_out_right)
         */

        // navigate with action for safe arguments
        /*view.findViewById<Button>(R.id.animSport)?.setOnClickListener {
            //val flowStepNumberArg = 1
            val action = HomeFragmentDirections.actionHomeFragmentToSportFragment()
            findNavController().navigate(action)
        }
        */

    }

    /*
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
    }
    */
}