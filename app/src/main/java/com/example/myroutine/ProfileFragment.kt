package com.example.myroutine

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
class ProfileFragment : Fragment() {
    var name = "user"
    var age = 0.0f
    var weight = 0.0f
    var height = 0.0f
    var sex = "none"
    var sleepHoursADay = 0.0f
    var workPalValue = 0.0f
    var workHoursADay = 0.0f
    var sportPalValue = 0.0f
    var sportHoursADay = 0.0f
    var bmi = 0.0f
    var bmiColor = 0
    var bmiCategory = "none"
    var dailyenergyneedkcal = 0.0f
    var dailyEnergyNeedKj = 0.0f
    private val calculations = BodyCalculations()
    private lateinit var sexSpinnerOptions: Array<String>
    private lateinit var workEnergyNeedSpinnerOptions: Array<String>
    private lateinit var sportEnergyNeedSpinnerOptions: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // create DHHelper object
        Utility.helper = DBHelper(context as Activity)
        // get write access for db
        Utility.db = Utility.helper.writableDatabase
        checkIfTableAndUserExistInDB()
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
        sexSpinnerOptions = arrayOf(getString(R.string.pickYourSex),getString(R.string.woman),getString(R.string.man),getString(R.string.divers))
        workEnergyNeedSpinnerOptions = arrayOf(getString(R.string.palSleep), getString(R.string.palJustSittingOrLayingActivity), getString(R.string.palMostlySittingOrLayingActivity), getString(R.string.palSomeWalkingActivity), getString(R.string.palMostlyWalkingOrStandingActivity), getString(R.string.palPhysicalDemandingActivity), getString(R.string.palPhysicalVeryDemandingActivity))
        sportEnergyNeedSpinnerOptions = arrayOf(getString(R.string.palVeryLightSport), getString(R.string.palLightSport), getString(R.string.palModerateSport), getString(R.string.palPhysicalDemandingSport), getString(R.string.palVeryLightSport), getString(R.string.palCompetitiveSport))
        createSpinnerFunction(spinnerViewId = R.id.sp_your_sex, spinnerOptions = sexSpinnerOptions)
        createSpinnerFunction(spinnerViewId = R.id.sp_work_pal_value, spinnerOptions = workEnergyNeedSpinnerOptions)
        createSpinnerFunction(spinnerViewId = R.id.sp_sport_pal_value, spinnerOptions = sportEnergyNeedSpinnerOptions)
        //readUserDataFromDbAndUpdateProfileViews()

        // save user data into db
        view.findViewById<Button>(R.id.btn_save_user_data_and_show_body_calculations)?.setOnClickListener {
            calculatBodyMetrics()
            updateUserDataInDB()
            //navigateToBodyCalculationsFragment()
        }

        view.findViewById<Button>(R.id.btn_show_body_calculations)?.setOnClickListener {
            calculatBodyMetrics()
            navigateToBodyCalculationsFragment()
        }
    }

    private fun navigateToBodyCalculationsFragment() {
        val action = bmiCategory.let {
            ProfileFragmentDirections.actionProfileFragmentToBodyCalculationsFragment(bmi, bmiColor,
                it, dailyenergyneedkcal, dailyEnergyNeedKj)
        }
        val navOptions: NavOptions = NavOptions.Builder()
            .setPopUpTo(R.id.profileFragment, inclusive = false, saveState = true)
            .build()
        if (action != null) {
            findNavController().navigate(action, navOptions)
        }
    }

    private fun calculatBodyMetrics(){
        getValuesFromViews()
        if (weight != 0.0f && height != 0.0f){
                bmi = calculations.bmiCalculator(weight = weight.toString().toFloat(), height = height.toString().toFloat())
                bmiColor = calculations.bmiColor(bmi = bmi, context = context as Activity)
                bmiCategory = calculations.bmiCategory(bmi = bmi, context = context as Activity)

            if (age != 0.0f && (sex == getString(R.string.man) || sex == getString(R.string.woman)) && sleepHoursADay != 0.0f && workHoursADay != 0.0f && sportHoursADay != 0.0f){
                dailyenergyneedkcal = calculations.energyNeedCalculatorKcal(weight = weight, height = height, sex = sex, age = age, sleepHoursADay = sleepHoursADay, workPalValue = workPalValue, workHoursADay = workHoursADay, sportPalValue = sportPalValue, sportHoursADay = sportHoursADay)
                dailyEnergyNeedKj = calculations.kcalToKjConverter(dailyenergyneedkcal)
            }
        }

    }

    private fun getValuesFromViews(){
        val calculations = BodyCalculations()
        val regexFloat = "\\d+(\\.\\d+)?".toRegex()
        val nameView = view?.findViewById<EditText>(R.id.et_your_name)?.text.toString()
        val weightView = view?.findViewById<EditText>(R.id.et_your_weight)?.text.toString()
        val heightView = view?.findViewById<EditText>(R.id.et_your_height)?.text.toString()
        val ageView = view?.findViewById<EditText>(R.id.et_your_age)?.text.toString()
        val sexView = view?.findViewById<Spinner>(R.id.sp_your_sex)?.selectedItem.toString()
        val sleepHoursADayView = view?.findViewById<EditText>(R.id.et_your_sleep_hours)?.text.toString()
        workPalValue = calculations.palCategoryToPalValueWork(palCategory = view?.findViewById<Spinner>(R.id.sp_work_pal_value)?.selectedItem.toString(),context = context as Activity)
        val workHoursADayView = view?.findViewById<EditText>(R.id.et_your_work_hours)?.text.toString()
        sportPalValue = calculations.palCategoryToPalValueWork(palCategory = view?.findViewById<Spinner>(R.id.sp_sport_pal_value)?.selectedItem.toString(), context = context as Activity)
        val sportHoursADayView = view?.findViewById<EditText>(R.id.et_your_sport_hours)?.text.toString()

        if(nameView.isNotEmpty()){
            name = nameView
        }
        if(weightView.matches(regexFloat)){
            weight = weightView.toFloat()
        }
        if(heightView.matches(regexFloat)){
            height = heightView.toFloat()
        }
        if(ageView.matches(regexFloat)){
            age = ageView.toFloat()
        }
        if(sexView != getString(R.string.pickYourSex)){
            sex = sexView
        }
        if(sleepHoursADayView.matches(regexFloat)){
            sleepHoursADay = sleepHoursADayView.toFloat()
        }
        if(workHoursADayView.matches(regexFloat)){
            workHoursADay = workHoursADayView.toFloat()
        }
        if(sportHoursADayView.matches(regexFloat)){
            sportHoursADay = sportHoursADayView.toFloat()
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

    private fun checkIfTableAndUserExistInDB(){
        // check if table USER exists
        var rd = Utility.db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='USER'", null)
        if(rd.count == 0){
            // table USER does not exist, create table and standard user
            rd = Utility.db.rawQuery("CREATE TABLE USER(USERID INTEGER PRIMARY KEY, NAME TEXT, AGE FLOAT, WEIGHT FLOAT, HEIGHT FLOAT, SEX FLOAT, SLEEP_HOURS_A_DAY FLOAT, WORK_HOURS_A_DAY FLOAT, WORK_PAL_VALUE FLOAT, SPORT_HOURS FLOAT_A_DAY, SPORT_PAL_VALUE FLOAT, BMI FLOAT, DAILY_ENERGY_NEED_KCAL FLOAT)", null)
            rd = Utility.db.rawQuery("INSERT INTO USER(USERID,NAME,AGE,WEIGHT,HEIGHT,SEX,SLEEP_HOURS_A_DAY,WORK_HOURS_A_DAY,WORK_PAL_VALUE,SPORT_HOURS,SPORT_PAL_VALUE,BMI,DAILY_ENERGY_NEED_KCAL) VALUES(0,'user',0,0,0,'none',0,0,0,0,0,0,0)", null)
        }else{
            // table USER does exist, check if standard user exists
            rd = Utility.db.rawQuery("SELECT EXISTS(SELECT 1 FROM USER WHERE USERID=0 LIMIT 1)", null)
            if(rd.count == 0){
                // standard user does not exist, create standard user
                rd = Utility.db.rawQuery("INSERT INTO USER(USERID,NAME,AGE,WEIGHT,HEIGHT,SEX,SLEEP_HOURS_A_DAY,WORK_HOURS_A_DAY,WORK_PAL_VALUE,SPORT_HOURS,SPORT_PAL_VALUE,BMI,DAILY_ENERGY_NEED_KCAL) VALUES(0,'user',0,0,0,'Pick your sex',0,0,0,0,0,0,0)", null)
            }
        }
        rd.close()
    }
    private fun updateUserDataInDB(){
        // update user data in db
        val rd = Utility.db.rawQuery("UPDATE USER SET NAME = name,AGE = age,WEIGHT = weight,HEIGHT = height,SEX = sex,SLEEP_HOURS_A_DAY = sleepHoursADay,WORK_HOURS_A_DAY = workHoursADay,WORK_PAL_VALUE = workPalValue,SPORT_HOURS = sportHoursADay,SPORT_PAL_VALUE = sportPalValue,BMI = bmi,DAILY_ENERGY_NEED_KCAL = dailyEnergyNeedKcal WHERE USERID = 0", null)
        rd.close()
    }

    private fun readUserDataFromDbAndUpdateProfileViews(){
        // read user data from db
        val rd = Utility.db.rawQuery("SELECT USERID,NAME,AGE,WEIGHT,HEIGHT,SEX,SLEEP_HOURS_A_DAY,WORK_HOURS_A_DAY,WORK_PAL_VALUE,SPORT_HOURS,SPORT_PAL_VALUE,BMI,DAILY_ENERGY_NEED_KCAL FROM USER WHERE USERID = 0", null)
        // update profile views
        view?.findViewById<EditText>(R.id.et_your_name)?.setText(rd.getString(1))
        view?.findViewById<EditText>(R.id.et_your_age)?.setText(rd.getString(2))
        view?.findViewById<EditText>(R.id.et_your_weight)?.setText(rd.getString(3))
        view?.findViewById<EditText>(R.id.et_your_height)?.setText(rd.getString(4))
        val sexValueIndex = sexSpinnerOptions.indexOf(rd.getString(5))
        view?.findViewById<Spinner>(R.id.sp_your_sex)?.setSelection(sexValueIndex)
        view?.findViewById<EditText>(R.id.et_your_sleep_hours)?.setText(rd.getString(6))
        val workPalValueIndex = workEnergyNeedSpinnerOptions.indexOf(rd.getString(7))
        val workPalValue = view?.findViewById<Spinner>(R.id.sp_work_pal_value)?.setSelection(workPalValueIndex)
        view?.findViewById<EditText>(R.id.et_your_work_hours)?.setText(rd.getString(8))
        val sportPalValueIndex = sportEnergyNeedSpinnerOptions.indexOf(rd.getString(9))
        val sportPalValue = view?.findViewById<Spinner>(R.id.sp_sport_pal_value)?.setSelection(sportPalValueIndex)
        view?.findViewById<EditText>(R.id.et_your_sport_hours)?.setText(rd.getString(10))
        rd.close()
    }
}