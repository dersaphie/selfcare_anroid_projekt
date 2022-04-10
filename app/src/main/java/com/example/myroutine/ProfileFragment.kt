package com.example.myroutine

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController

class ProfileFragment : Fragment() {
    private lateinit var calculations: BodyCalculations
    private lateinit var sexSpinnerOptions: Array<String>
    private lateinit var workEnergyNeedSpinnerOptions: Array<String>
    private lateinit var sportEnergyNeedSpinnerOptions: Array<String>
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            user = User(context as Activity)
            user.checkIfTableAndUserExistInDB()
            calculations = BodyCalculations(context as Activity)
        }catch (e: Exception){
            Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
        }finally {
            navigateToHomeFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // set title of top action bar
        (activity as AppCompatActivity).supportActionBar?.title = this.getString(R.string.profile)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sexSpinnerOptions = arrayOf(getString(R.string.pickYourSex),getString(R.string.woman),getString(R.string.man),getString(R.string.divers))
        workEnergyNeedSpinnerOptions = arrayOf(getString(R.string.pickYourWorkEnergyNeed), getString(R.string.palSleep), getString(R.string.palJustSittingOrLayingActivity), getString(R.string.palMostlySittingOrLayingActivity), getString(R.string.palSomeWalkingActivity), getString(R.string.palMostlyWalkingOrStandingActivity), getString(R.string.palPhysicalDemandingActivity), getString(R.string.palPhysicalVeryDemandingActivity))
        sportEnergyNeedSpinnerOptions = arrayOf(getString(R.string.pickYourSportEnergyNeed), getString(R.string.palVeryLightSport), getString(R.string.palLightSport), getString(R.string.palModerateSport), getString(R.string.palPhysicalDemandingSport), getString(R.string.palVeryLightSport), getString(R.string.palCompetitiveSport))
        createSpinnerFunction(spinnerViewId = R.id.sp_your_sex, spinnerOptions = sexSpinnerOptions)
        createSpinnerFunction(spinnerViewId = R.id.sp_work_pal_value, spinnerOptions = workEnergyNeedSpinnerOptions)
        createSpinnerFunction(spinnerViewId = R.id.sp_sport_pal_value, spinnerOptions = sportEnergyNeedSpinnerOptions)
        try {
            readUserDataFromDbAndUpdateProfileViews()
        }catch (e: Exception){
            Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
        }finally {
            navigateToHomeFragment()
        }

        // save user data into db
        view.findViewById<Button>(R.id.btn_save_user_data_and_show_body_calculations)?.setOnClickListener {
            try {
                getValuesFromViews()
                user.updateUserDataInDB()
                calculateBodyMetrics()
                navigateToBodyCalculationsFragment()
            }catch (e: Exception){
                Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
            }finally {
                navigateToHomeFragment()
            }
        }

        // only show the calculation results without saving them
        view.findViewById<Button>(R.id.btn_show_body_calculations)?.setOnClickListener {
            try {
                getValuesFromViews()
                calculateBodyMetrics()
                navigateToBodyCalculationsFragment()
            }catch (e: Exception){
                Toast.makeText(context,e.message.toString(), Toast.LENGTH_SHORT).show()
            }finally {
                navigateToHomeFragment()
            }
        }
    }

    private fun navigateToBodyCalculationsFragment() {
        val action = user.bmiCategory.let {
            ProfileFragmentDirections.actionProfileFragmentToBodyCalculationsFragment(user.bmi, user.bmiColor,
                it, user.dailyEnergyKcalAndKjString)
        }
        val navOptions: NavOptions = NavOptions.Builder()
            .setPopUpTo(R.id.profileFragment, inclusive = false, saveState = true)
            .build()
        findNavController().navigate(action, navOptions)
    }

    private fun navigateToHomeFragment() {
    }

    private fun calculateBodyMetrics(){
        if (user.weight > 0.0f && user.height > 0.0f){
                user.bmi = calculations.bmiCalculator(weight = user.weight.toString().toFloat(), height = user.height.toString().toFloat())
                user.bmiColor = calculations.bmiColor(bmi = user.bmi, context = context as Activity)
                user.bmiCategory = calculations.bmiCategory(bmi = user.bmi, context = context as Activity)

            if (user.age > 0) {
                if (user.sex == getString(R.string.man) || user.sex == getString(R.string.woman) && user.sleepHoursADay >= 0.0f && user.workHoursADay >= 0.0f && user.sportHoursADay >= 0.0f && user.workPalValue != 0.0f && user.sportPalValue != 0.0f){
                    user.dailyEnergyNeedKcal = calculations.energyNeedCalculatorKcal(weight = user.weight, height = user.height, sex = user.sex, age = user.age, sleepHoursADay = user.sleepHoursADay, workPalValue = user.workPalValue, workHoursADay = user.workHoursADay, sportPalValue = user.sportPalValue, sportHoursADay = user.sportHoursADay)
                    user.dailyEnergyNeedKj = calculations.kcalToKjConverter(user.dailyEnergyNeedKcal)
                    user.dailyEnergyKcalAndKjString = user.dailyEnergyNeedKj.toString() + context?.getString(R.string.unitForKjValueWithLeadingSpace) + context?.getString(R.string.spacerBetweenKjValueAndKcalValue) + user.dailyEnergyNeedKcal.toString() + context?.getString(R.string.unitForKcalValueWithLeadingSpace)
                }
                if (user.sex == getString(R.string.divers)){
                    // range of dailyEnergyNeedKj for woman to dailyEnergyNeedKj for man
                    user.dailyEnergyNeedKcal = calculations.energyNeedCalculatorKcal(weight = user.weight, height = user.height, sex = "Woman", age = user.age, sleepHoursADay = user.sleepHoursADay, workPalValue = user.workPalValue, workHoursADay = user.workHoursADay, sportPalValue = user.sportPalValue, sportHoursADay = user.sportHoursADay)
                    user.dailyEnergyNeedKj = calculations.kcalToKjConverter(user.dailyEnergyNeedKcal)
                    user.dailyEnergyKcalAndKjString = user.dailyEnergyNeedKj.toString() + context?.getString(R.string.unitForKjValueWithLeadingSpace) + context?.getString(R.string.spacerBetweenKjValueAndKcalValue) + user.dailyEnergyNeedKcal.toString() + context?.getString(R.string.unitForKcalValueWithLeadingSpace)
                    user.dailyEnergyNeedKcal = calculations.energyNeedCalculatorKcal(weight = user.weight, height = user.height, sex = "Man", age = user.age, sleepHoursADay = user.sleepHoursADay, workPalValue = user.workPalValue, workHoursADay = user.workHoursADay, sportPalValue = user.sportPalValue, sportHoursADay = user.sportHoursADay)
                    user.dailyEnergyNeedKj = calculations.kcalToKjConverter(user.dailyEnergyNeedKcal)
                    user.dailyEnergyKcalAndKjString += context?.getString(R.string.spacerBetweenTwoKjValues) + "\n" + user.dailyEnergyNeedKj.toString() + context?.getString(R.string.unitForKjValueWithLeadingSpace) + context?.getString(R.string.spacerBetweenKjValueAndKcalValue) + user.dailyEnergyNeedKcal.toString() + context?.getString(R.string.unitForKcalValueWithLeadingSpace)
                }
            }
        }
    }

    private fun getValuesFromViews(){
        val regexFloat = "\\d+(\\.\\d+)?".toRegex()
        val regexInt = "\\d+?".toRegex()
        val nameView = view?.findViewById<EditText>(R.id.et_your_name)?.text.toString()
        val weightView = view?.findViewById<EditText>(R.id.et_your_weight)?.text.toString()
        val heightView = view?.findViewById<EditText>(R.id.et_your_height)?.text.toString()
        val ageView = view?.findViewById<EditText>(R.id.et_your_age)?.text.toString()
        val sexView = view?.findViewById<Spinner>(R.id.sp_your_sex)?.selectedItem.toString()
        val sleepHoursADayView = view?.findViewById<EditText>(R.id.et_your_sleep_hours)?.text.toString()
        user.workPalValue = calculations.palCategoryToPalValueWork(palCategory = view?.findViewById<Spinner>(R.id.sp_work_pal_value)?.selectedItem.toString(),context = context as Activity)
        val workHoursADayView = view?.findViewById<EditText>(R.id.et_your_work_hours)?.text.toString()
        user.sportPalValue = calculations.palCategoryToPalValueSport(palCategory = view?.findViewById<Spinner>(R.id.sp_sport_pal_value)?.selectedItem.toString(), context = context as Activity)
        val sportHoursADayView = view?.findViewById<EditText>(R.id.et_your_sport_hours)?.text.toString()

        if(nameView.isNotEmpty()){
            user.name = nameView
        }else{
            throw IllegalArgumentException(this.getString(R.string.errorMessageUserNameEmpty))
        }
        if(ageView.matches(regexInt)){
            user.age = ageView.toInt()
        }else{
            throw IllegalArgumentException(this.getString(R.string.errorMessageAgeMinimum))
        }
        if(weightView.matches(regexFloat)){
            user.weight = weightView.toFloat()
        }else{
            throw IllegalArgumentException(this.getString(R.string.errorMessageWeightMinimum))
        }
        if(heightView.matches(regexFloat)){
            user.height = heightView.toFloat()
        }else{
            throw IllegalArgumentException(this.getString(R.string.errorMessageHeightMinimum))
        }
        if(sexView != getString(R.string.pickYourSex)){
            user.sex = sexView
        }
        if(sleepHoursADayView.matches(regexFloat)){
            if(user.sleepHoursADay+user.workHoursADay+user.sportHoursADay>24)
            {
                throw IllegalArgumentException(this.getString(R.string.errorMessageSleepSportWorkHoursCombinedOver24Hours))
            }
            user.sleepHoursADay = sleepHoursADayView.toFloat()
        }
        if(workHoursADayView.matches(regexFloat)){
            if(user.sleepHoursADay+user.workHoursADay+user.sportHoursADay>24)
            {
                throw IllegalArgumentException(this.getString(R.string.errorMessageSleepSportWorkHoursCombinedOver24Hours))
            }
            user.workHoursADay = workHoursADayView.toFloat()
        }
        if(sportHoursADayView.matches(regexFloat)){
            if(user.sleepHoursADay+user.workHoursADay+user.sportHoursADay>24)
            {
                throw IllegalArgumentException(this.getString(R.string.errorMessageSleepSportWorkHoursCombinedOver24Hours))
            }
            user.sportHoursADay = sportHoursADayView.toFloat()
        }
    }

    private fun createSpinnerFunction(spinnerViewId: Int, spinnerOptions: Array<String>)
    {
        // sport selection
        // find spinner view
        val spinner = view?.findViewById(spinnerViewId) as Spinner

        // layout for spinner
        val spinnerLayout = android.R.layout.simple_spinner_dropdown_item

        // set array in adapter
        val adapter = context?.let { ArrayAdapter(it,spinnerLayout,spinnerOptions) }

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

    private fun readUserDataFromDbAndUpdateProfileViews(){
        // read user data from db
        user.readUserDataFromDb()
        // update profile views
        view?.findViewById<EditText>(R.id.et_your_name)?.setText(user.name)
        view?.findViewById<EditText>(R.id.et_your_age)?.setText(user.age.toString())
        view?.findViewById<EditText>(R.id.et_your_weight)?.setText(user.weight.toString())
        view?.findViewById<EditText>(R.id.et_your_height)?.setText(user.height.toString())
        val sexValueIndex = sexSpinnerOptions.indexOf(user.sex)
        view?.findViewById<Spinner>(R.id.sp_your_sex)?.setSelection(sexValueIndex)
        view?.findViewById<EditText>(R.id.et_your_sleep_hours)?.setText(user.sleepHoursADay.toString())
        val workPalValueIndex = workEnergyNeedSpinnerOptions.indexOf(calculations.palValueToPalCategoryWork(user.workPalValue, context as Activity))
        view?.findViewById<Spinner>(R.id.sp_work_pal_value)?.setSelection(workPalValueIndex)
        view?.findViewById<EditText>(R.id.et_your_work_hours)?.setText(user.workHoursADay.toString())
        val sportPalValueIndex = sportEnergyNeedSpinnerOptions.indexOf(calculations.palValueToPalCategorySport(user.sportPalValue, context as Activity))
        view?.findViewById<Spinner>(R.id.sp_sport_pal_value)?.setSelection(sportPalValueIndex)
        view?.findViewById<EditText>(R.id.et_your_sport_hours)?.setText(user.sportHoursADay.toString())
    }
}