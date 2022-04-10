package com.example.myroutine

import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController

/**
 * functionallity of buttons is definded in Home Fragment
 */


class HomeFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // set title of top action bar
        (activity as AppCompatActivity).supportActionBar?.title = this.getString(R.string.home)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.btnSport)?.setOnClickListener {
            val navOptionsHomeFragmentToSportFragment: NavOptions = NavOptions.Builder()
                .setPopUpTo(R.id.homeFragment, inclusive = false, saveState = true)
                .build()
            findNavController().navigate(
                HomeFragmentDirections.actionHomeToSport(),
                navOptionsHomeFragmentToSportFragment
            )
        }
        view.findViewById<Button>(R.id.btnProfile)?.setOnClickListener {
            val navOptionsHomeFragmentToProfileFragment: NavOptions = NavOptions.Builder()
                .setPopUpTo(R.id.homeFragment, inclusive = false, saveState = true)
                .build()
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToProfileFragment(),
                navOptionsHomeFragmentToProfileFragment
            )
        }
    }
}