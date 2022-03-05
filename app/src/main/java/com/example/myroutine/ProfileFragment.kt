package com.example.myroutine

import android.content.ContentValues
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

    private fun updateAndShowBodyCalculations(){
        val calculations = Calculations()
        val regexFloat = "-?\\d+(\\.\\d+)?".toRegex()
        var bmi = "Needed values for calculation are incomplete"
        var dailyEnergyNeed = "Needed values for calculation are incomplete"
        val weight = view?.findViewById<EditText>(R.id.et_your_weight)?.text
        val height = view?.findViewById<EditText>(R.id.et_your_height)?.text
        if (weight != null){
            if (weight.matches(regexFloat) && height != null && height.matches(regexFloat)) {
                bmi = calculations.BMICalculator(weight = weight.toString().toFloat(), height = height.toString().toFloat()).toString()
            }
            if (true){
                //calculations.EnergyNeedCalculator(weight = weight.toString().toFloat(),)
            }
        }
        val action = ProfileFragmentDirections.actionProfileFragmentToBodyCalculationsFragment(bmi.toString(), dailyEnergyNeed.toString())
        val navOptions: NavOptions = NavOptions.Builder()
            .setPopUpTo(R.id.profileFragment, inclusive = false, saveState = true)
            .build()
        findNavController().navigate(action, navOptions)
    }

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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

        //Find View by id for Spinner and Button
        val spinner=view.findViewById<Spinner>(R.id.sp_your_sport) as Spinner

        //Create An Array which Contain Country name
        val country= arrayOf("Kabaland","India","United States of die Amerikas","Canada","Brazil")

        //Set Array in Adapter
        val adapter=
            ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item,country)

        spinner.adapter=adapter

        spinner.onItemSelectedListener=object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                Toast.makeText(
                    requireContext(),
                    "You have Selected " + country[p2],
                    Toast.LENGTH_LONG
                ).show()

            }
        }

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
}