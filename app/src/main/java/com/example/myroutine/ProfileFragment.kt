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
    private var name = "user"
    private var age = 0
    private var weight = 0.0f
    private var height = 0f
    private var sex = "none"
    private var sleepHoursADay = 0.0f
    private var workPalValue = 0.0f
    private var workHoursADay = 0.0f
    private var sportPalValue = 0.0f
    private var sportHoursADay = 0.0f
    private var bmi = 0.0f
    private var bmiColor = 0
    private var bmiCategory = "none"
    private var dailyEnergyNeedKcal = 0.0f
    private var dailyEnergyNeedKj = 0.0f
    private var dailyEnergyKcalAndKjString = "0.0 kcal / 0.0 kj"
    private val calculations = BodyCalculations()
    private lateinit var sexSpinnerOptions: Array<String>
    private lateinit var workEnergyNeedSpinnerOptions: Array<String>
    private lateinit var sportEnergyNeedSpinnerOptions: Array<String>
    private lateinit var db: UserRoomDatabase
    private lateinit var userDao: UserDao
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = UserRoomDatabase.getDatabase(context as Activity)
        //db = userApplication.database
        userDao = db.userDao()
        user = User()
        // delete user for development
        //userDao.delete(userDao.selectAllById(0))
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
        workEnergyNeedSpinnerOptions = arrayOf(getString(R.string.pickYourWorkEnergyNeed), getString(R.string.palSleep), getString(R.string.palJustSittingOrLayingActivity), getString(R.string.palMostlySittingOrLayingActivity), getString(R.string.palSomeWalkingActivity), getString(R.string.palMostlyWalkingOrStandingActivity), getString(R.string.palPhysicalDemandingActivity), getString(R.string.palPhysicalVeryDemandingActivity))
        sportEnergyNeedSpinnerOptions = arrayOf(getString(R.string.pickYourSportEnergyNeed), getString(R.string.palVeryLightSport), getString(R.string.palLightSport), getString(R.string.palModerateSport), getString(R.string.palPhysicalDemandingSport), getString(R.string.palVeryLightSport), getString(R.string.palCompetitiveSport))
        createSpinnerFunction(spinnerViewId = R.id.sp_your_sex, spinnerOptions = sexSpinnerOptions)
        createSpinnerFunction(spinnerViewId = R.id.sp_work_pal_value, spinnerOptions = workEnergyNeedSpinnerOptions)
        createSpinnerFunction(spinnerViewId = R.id.sp_sport_pal_value, spinnerOptions = sportEnergyNeedSpinnerOptions)
        readUserDataFromDbAndUpdateProfileViews()

        // save user data into db
        view.findViewById<Button>(R.id.btn_save_user_data_and_show_body_calculations)?.setOnClickListener {
            calculateBodyMetrics()
            updateUserDataInDB()
            navigateToBodyCalculationsFragment()
        }

        view.findViewById<Button>(R.id.btn_show_body_calculations)?.setOnClickListener {
            calculateBodyMetrics()
            navigateToBodyCalculationsFragment()
        }
    }

    private fun navigateToBodyCalculationsFragment() {
        val action = bmiCategory.let {
            ProfileFragmentDirections.actionProfileFragmentToBodyCalculationsFragment(bmi, bmiColor,
                it, dailyEnergyKcalAndKjString)
        }
        val navOptions: NavOptions = NavOptions.Builder()
            .setPopUpTo(R.id.profileFragment, inclusive = false, saveState = true)
            .build()
        findNavController().navigate(action, navOptions)
    }

    private fun calculateBodyMetrics(){
        getValuesFromViews()
        if (weight > 0.0f && height > 0.0f){
                bmi = calculations.bmiCalculator(weight = weight.toString().toFloat(), height = height.toString().toFloat())
                bmiColor = calculations.bmiColor(bmi = bmi, context = context as Activity)
                bmiCategory = calculations.bmiCategory(bmi = bmi, context = context as Activity)

            if (age > 0) {
                if (sex == getString(R.string.man) || sex == getString(R.string.woman) && sleepHoursADay >= 0.0f && workHoursADay >= 0.0f && sportHoursADay >= 0.0f && workPalValue != 0.0f && sportPalValue != 0.0f){
                    dailyEnergyNeedKcal = calculations.energyNeedCalculatorKcal(weight = weight, height = height, sex = sex, age = age, sleepHoursADay = sleepHoursADay, workPalValue = workPalValue, workHoursADay = workHoursADay, sportPalValue = sportPalValue, sportHoursADay = sportHoursADay)
                    dailyEnergyNeedKj = calculations.kcalToKjConverter(dailyEnergyNeedKcal)
                    dailyEnergyKcalAndKjString = dailyEnergyNeedKj.toString() + context?.getString(R.string.unitForKjValueWithLeadingSpace) + context?.getString(R.string.spacerBetweenKjValueAndKcalValue) + dailyEnergyNeedKcal.toString() + context?.getString(R.string.unitForKcalValueWithLeadingSpace)
                }
                if (sex == getString(R.string.divers)){
                    // range of dailyEnergyNeedKj for woman to dailyEnergyNeedKj for man
                    dailyEnergyNeedKcal = calculations.energyNeedCalculatorKcal(weight = weight, height = height, sex = "Woman", age = age, sleepHoursADay = sleepHoursADay, workPalValue = workPalValue, workHoursADay = workHoursADay, sportPalValue = sportPalValue, sportHoursADay = sportHoursADay)
                    dailyEnergyNeedKj = calculations.kcalToKjConverter(dailyEnergyNeedKcal)
                    dailyEnergyKcalAndKjString = dailyEnergyNeedKj.toString() + context?.getString(R.string.unitForKjValueWithLeadingSpace) + context?.getString(R.string.spacerBetweenKjValueAndKcalValue) + dailyEnergyNeedKcal.toString() + context?.getString(R.string.unitForKcalValueWithLeadingSpace)
                    dailyEnergyNeedKcal = calculations.energyNeedCalculatorKcal(weight = weight, height = height, sex = "Man", age = age, sleepHoursADay = sleepHoursADay, workPalValue = workPalValue, workHoursADay = workHoursADay, sportPalValue = sportPalValue, sportHoursADay = sportHoursADay)
                    dailyEnergyNeedKj = calculations.kcalToKjConverter(dailyEnergyNeedKcal)
                    dailyEnergyKcalAndKjString += context?.getString(R.string.spacerBetweenTwoKjValues) + "\n" + dailyEnergyNeedKj.toString() + context?.getString(R.string.unitForKjValueWithLeadingSpace) + context?.getString(R.string.spacerBetweenKjValueAndKcalValue) + dailyEnergyNeedKcal.toString() + context?.getString(R.string.unitForKcalValueWithLeadingSpace)
                }
            }
        }
    }

    private fun getValuesFromViews(){
        val calculations = BodyCalculations()
        val regexFloat = "\\d+(\\.\\d+)?".toRegex()
        val regexInt = "\\d+?".toRegex()
        val nameView = view?.findViewById<EditText>(R.id.et_your_name)?.text.toString()
        val weightView = view?.findViewById<EditText>(R.id.et_your_weight)?.text.toString()
        val heightView = view?.findViewById<EditText>(R.id.et_your_height)?.text.toString()
        val ageView = view?.findViewById<EditText>(R.id.et_your_age)?.text.toString()
        val sexView = view?.findViewById<Spinner>(R.id.sp_your_sex)?.selectedItem.toString()
        val sleepHoursADayView = view?.findViewById<EditText>(R.id.et_your_sleep_hours)?.text.toString()
        workPalValue = calculations.palCategoryToPalValueWork(palCategory = view?.findViewById<Spinner>(R.id.sp_work_pal_value)?.selectedItem.toString(),context = context as Activity)
        val workHoursADayView = view?.findViewById<EditText>(R.id.et_your_work_hours)?.text.toString()
        sportPalValue = calculations.palCategoryToPalValueSport(palCategory = view?.findViewById<Spinner>(R.id.sp_sport_pal_value)?.selectedItem.toString(), context = context as Activity)
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
        if(ageView.matches(regexInt)){
            age = ageView.toInt()
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
        // create user in user table if it does not exist
        if (userDao.getAll().isEmpty()) {
            val user = User(0, name, age, weight, height, getString(R.string.pickYourSex), sleepHoursADay, sportPalValue,sportHoursADay,workPalValue,workHoursADay)
            userDao.insertAll(user)
        }
    }
    private fun updateUserDataInDB(){
        // update user data in db
        val user = User(0,name,age,weight,height,sex,sleepHoursADay,sportPalValue,sportHoursADay,workPalValue,workHoursADay)
        userDao.updateUsers(user)
    }

    private fun readUserDataFromDbAndUpdateProfileViews(){
        // read user data from db
        val user = userDao.selectAllById(0)
        // update profile views
        view?.findViewById<EditText>(R.id.et_your_name)?.setText(user.name)
        view?.findViewById<EditText>(R.id.et_your_age)?.setText(user.age.toString())
        view?.findViewById<EditText>(R.id.et_your_weight)?.setText(user.weight.toString())
        view?.findViewById<EditText>(R.id.et_your_height)?.setText(user.height.toString())
        val sexValueIndex = sexSpinnerOptions.indexOf(user.sex)
        view?.findViewById<Spinner>(R.id.sp_your_sex)?.setSelection(sexValueIndex)
        view?.findViewById<EditText>(R.id.et_your_sleep_hours)?.setText(user.sleep_hours.toString())
        val workPalValueIndex = workEnergyNeedSpinnerOptions.indexOf(calculations.palValueToPalCategoryWork(user.work_energy_need, context as Activity))
        view?.findViewById<Spinner>(R.id.sp_work_pal_value)?.setSelection(workPalValueIndex)
        view?.findViewById<EditText>(R.id.et_your_work_hours)?.setText(user.work_hours.toString())
        val sportPalValueIndex = sportEnergyNeedSpinnerOptions.indexOf(calculations.palValueToPalCategorySport(user.sport_energy_need, context as Activity))
        view?.findViewById<Spinner>(R.id.sp_sport_pal_value)?.setSelection(sportPalValueIndex)
        view?.findViewById<EditText>(R.id.et_your_sport_hours)?.setText(user.sport_hours.toString())
    }
}