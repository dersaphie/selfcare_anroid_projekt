package com.example.myroutine

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.navArgs


class BodyCalculationsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bodycalculations, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val safeArgs: BodyCalculationsFragmentArgs by navArgs()
        val bmi = safeArgs.bmi
        val bmiColor = safeArgs.bmiColor
        val bmiCategory = safeArgs.bmiCategory
        val dailyEnergyNeedKcal = safeArgs.dailyEnergyNeedKcal
        val dailyEnergyNeedKj = safeArgs.dailyEnergyNeedKj
        val dailyEnergyKcalAndKjString = dailyEnergyNeedKcal.toString() + " " + context?.getString(R.string.spacerBetweenKcalValueAndKjValue) + " " + dailyEnergyNeedKj + " " + context?.getString(R.string.unitForKjValue)
        view.findViewById<TextView>(R.id.tv_your_bmi_value)?.text = bmi.toString()
        view.findViewById<TextView>(R.id.tv_your_bmi_category_value)?.setTextColor(bmiColor)
        view.findViewById<TextView>(R.id.tv_your_bmi_category_value)?.text = bmiCategory
        view.findViewById<TextView>(R.id.tv_your_daily_energy_need_value)?.text = dailyEnergyKcalAndKjString
    }
}