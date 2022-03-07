package com.example.myroutine

import android.app.Activity
import android.content.ContentValues
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
class ProfileFragment : Fragment() {
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
        val sexSpinnerOptions = arrayOf(getString(R.string.pickYourSex),getString(R.string.woman),getString(R.string.man),getString(R.string.divers))
        val workEnergyNeedSpinnerOptions = arrayOf(getString(R.string.palSleep), getString(R.string.palJustSittingOrLayingActivity), getString(R.string.palMostlySittingOrLayingActivity), getString(R.string.palSomeWalkingActivity), getString(R.string.palMostlyWalkingOrStandingActivity), getString(R.string.palPhysicalDemandingActivity), getString(R.string.palPhysicalVeryDemandingActivity))
        val sportEnergyNeedSpinnerOptions = arrayOf(getString(R.string.palVeryLightSport), getString(R.string.palLightSport), getString(R.string.palModerateSport), getString(R.string.palPhysicalDemandingSport), getString(R.string.palVeryLightSport), getString(R.string.palCompetitiveSport))
        createSpinnerFunction(spinnerViewId = R.id.sp_your_sex, spinnerOptions = sexSpinnerOptions)
        createSpinnerFunction(spinnerViewId = R.id.sp_work_pal_value, spinnerOptions = workEnergyNeedSpinnerOptions)
        createSpinnerFunction(spinnerViewId = R.id.sp_sport_pal_value, spinnerOptions = sportEnergyNeedSpinnerOptions)

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
        var bmiCategory = context.getString(R.string.valuesForBMIAreIncomplete)
        var dailyEnergyNeedKcal = 0.0f
        var dailyEnergyNeedKj = 0.0f

        // get values from user input
        val weight = view?.findViewById<EditText>(R.id.et_your_weight)?.text
        val height = view?.findViewById<EditText>(R.id.et_your_height)?.text
        val age = view?.findViewById<EditText>(R.id.et_your_age)?.text
        val sex = view?.findViewById<Spinner>(R.id.sp_your_sex)?.selectedItem.toString()
        val sleepHoursADay = view?.findViewById<EditText>(R.id.et_your_sleep_hours)?.text
        val workPalValue = view?.findViewById<Spinner>(R.id.sp_work_pal_value)?.selectedItem.toString()
        val workHoursADay = view?.findViewById<EditText>(R.id.et_your_work_hours)?.text
        val sportPalValue = view?.findViewById<Spinner>(R.id.sp_sport_pal_value)?.selectedItem.toString()
        val sportHoursADay = view?.findViewById<EditText>(R.id.et_your_sport_hours)?.text

        if (weight != null && weight.matches(regexFloat) && height != null && height.matches(regexFloat)){
                bmi = calculations.bmiCalculator(weight = weight.toString().toFloat(), height = height.toString().toFloat())
                bmiColor = calculations.bmiColor(bmi = bmi, context = context as Activity)
                bmiCategory = calculations.bmiCategory(bmi = bmi, context = context)

            if (age != null && age.matches(regexFloat) && (sex == "Man" || sex == "Woman")){
                dailyEnergyNeedKcal = calculations.energyNeedCalculatorKcal(weight = weight.toString().toFloat(), height = height.toString().toFloat(), sex = sex, age = age.toString().toFloat(), sleepHoursADay = sleepHoursADay.toString().toFloat(), workPalValue = calculations.palCategoryToPalValueWork(workPalValue,context), workHoursADay = workHoursADay.toString().toFloat(), calculations.palCategoryToPalValueWork(sportPalValue,context), sportHoursADay = sportHoursADay.toString().toFloat())
                dailyEnergyNeedKj = calculations.kcalToKjConverter(dailyEnergyNeedKcal)
            }
        }
        val action = ProfileFragmentDirections.actionProfileFragmentToBodyCalculationsFragment(bmi, bmiColor, bmiCategory, dailyEnergyNeedKcal, dailyEnergyNeedKj)
        val navOptions: NavOptions = NavOptions.Builder()
            .setPopUpTo(R.id.profileFragment, inclusive = false, saveState = true)
            .build()
        findNavController().navigate(action, navOptions)
    }

    private fun createSpinnerFunction(spinnerViewId: Int, spinnerOptions: Array<String>)
    {
        // sport selection
        // find spinner view
        val spinner = view?.findViewById(spinnerViewId) as Spinner

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
            }
        }
    }
}