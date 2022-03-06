package com.example.myroutine

import android.app.Activity
import android.content.ContentValues
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment profilFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO: Strings
        val sportSpinnerOptions = arrayOf(getString(R.string.cardio_without_equipment),getString(R.string.cardio_with_equipment),getString(R.string.muscle_building_without_equipment),getString(R.string.muscle_building_with_equipment))
        val workEnergyNeedSpinnerOptions = arrayOf(getString(R.string.lowEnergyNeed))
        val hobbyEnergyNeedSpinnerOptions = arrayOf(getString(R.string.lowEnergyNeed))
        createSpinnerFunction(spinnerViewId = R.id.sp_your_sport, spinnerOptions = sportSpinnerOptions)
        createSpinnerFunction(spinnerViewId = R.id.sp_work_energy_category, spinnerOptions = workEnergyNeedSpinnerOptions)
        createSpinnerFunction(spinnerViewId = R.id.sp_hobby_energy_category, spinnerOptions = workEnergyNeedSpinnerOptions)

        // save user data into db
        view.findViewById<Button>(R.id.btn_save_user_data_and_show_body_calculations)?.setOnClickListener {
            val cv = ContentValues()
            //var userNameTV = view.findViewById<Button>(R.id.et_your_name)
            //var userAgeTV = view.findViewById<Button>(R.id.et_your_age).text
            cv.put("NAME", R.id.et_your_name.toString())
            //cv.put("NAME", R.id.et_your_age.toString())
            cv.put("AGE", R.id.et_your_age.toString())
            //userNameTV.text = ""
            //userAgeTV.text = ""
            //binding.editTextNumberPassword.setText("")
            (activity as MainActivity?)!!.saveUserDataInDB(cv)
            updateAndShowBodyCalculations()
        }

        view.findViewById<Button>(R.id.btn_navigate_to_body_calculations_fragment)?.setOnClickListener {
            updateAndShowBodyCalculations()
        }
/*
        view.findViewById<Button>(R.id.btn_show_user_stats)?.setOnClickListener {
            val bmi = 1
            val action = ProfileFragmentDirections.actionProfileFragmentToResultsFragment(bmi)
            findNavController().navigate(action)
        }
 */


        /*
        // Navigate via destination
        view.findViewById<Button>(R.id.btn_show_user_stats)?.setOnClickListener(
        Navigation.createNavigateOnClickListener(R.id.resultsFragment, null)
        )
        */

    }

    private fun updateAndShowBodyCalculations(){
        // TODO: Strings
        val context = requireContext()
        val calculations = BodyCalculations()
        val regexFloat = "-?\\d+(\\.\\d+)?".toRegex()
        var bmi = 0.0f
        var bmiColor = 0
        var bmiCategory = "Needed values for calculation are incomplete"
        var dailyEnergyNeed = "Needed values for calculation are incomplete"
        val weight = view?.findViewById<EditText>(R.id.et_your_weight)?.text
        val height = view?.findViewById<EditText>(R.id.et_your_height)?.text
        if (weight != null){
            if (weight.matches(regexFloat) && height != null && height.matches(regexFloat)) {
                bmi = calculations.BMICalculator(weight = weight.toString().toFloat(), height = height.toString().toFloat())
                bmiColor = calculations.BMIColor(bmi = bmi, context = context as Activity)
                bmiCategory = calculations.BMICategory(bmi = bmi, context = context as Activity)
            }
            if (true){
                //calculations.EnergyNeedCalculator(weight = weight.toString().toFloat(),)
            }
        }
        val action = ProfileFragmentDirections.actionProfileFragmentToBodyCalculationsFragment(bmi, bmiColor, bmiCategory)
        val navOptions: NavOptions = NavOptions.Builder()
            .setPopUpTo(R.id.profileFragment, inclusive = false, saveState = true)
            .build()
        findNavController().navigate(action, navOptions)
    }

    private fun createSpinnerFunction(spinnerViewId: Int, spinnerOptions: Array<String>)
    {
        // sport selection
        // find spinner view
        val spinner = view?.findViewById<Spinner>(spinnerViewId) as Spinner

        // array of options
        //val spinnerOptions = arrayOf(getString(R.string.cardio_without_equipment),getString(R.string.cardio_with_equipment),getString(R.string.muscle_building_without_equipment),getString(R.string.muscle_building_with_equipment))

        // layout for spinner
        val spinnerLayout = android.R.layout.simple_spinner_dropdown_item

        // set array in adapter
        val adapter = ArrayAdapter(requireContext(),spinnerLayout,spinnerOptions)

        // set adapter for spinner
        spinner.adapter=adapter

        // create listener for user selection
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                /*
                Toast.makeText(
                    requireContext(),
                    "You have Selected " + spinnerOptions[p2],
                    Toast.LENGTH_LONG
                ).show()
                */
            }
        }
    }
}