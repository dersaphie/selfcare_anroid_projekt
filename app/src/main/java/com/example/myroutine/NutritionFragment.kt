package com.example.myroutine

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController

class NutritionFragment: Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nutrition, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        /*
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.btnMeasurement)?.setOnClickListener {
            val intent = Intent(view.context, Measurements::class.java)
            startActivity(intent)
        }
         */
        view.findViewById<Button>(R.id.btnMeasurement)?.setOnClickListener {
            //TODO: use the fun to show the body calculations
        }
    }
   /* override fun onDestroyView() {
        super.onDestroyView()
    }*/
}