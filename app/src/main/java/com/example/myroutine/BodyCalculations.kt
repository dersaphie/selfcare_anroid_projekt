package com.example.myroutine

import android.app.Activity
import java.math.RoundingMode
import java.text.DecimalFormat

class BodyCalculations {
    fun BMICalculator(weight: Float, height: Float):Float{
        return "%.2f".format(weight / (height/100.0f * height/100.0f)).toFloat()
        // BMI tables https://www.foodspring.de/bmi#vorteil
    }

    fun BMICategory(bmi: Float, context: Activity): String {
        val bmiCategory = when(bmi){
            0.0f -> context.getString(R.string.valuesForBMIAreIncomplete)
            in 0.01f..16.0f -> context.getString(R.string.severeUnderweight)
            in 16.01f..17.0f -> context.getString(R.string.moderateUnderweight)
            in 17.01f..18.5f -> context.getString(R.string.slightUnderweight)
            in 18.51f..25.0f -> context.getString(R.string.normalWeight)
            in 25.01f..30.0f -> context.getString(R.string.preadiposity)
            in 30.01f..35.0f -> context.getString(R.string.obesityGradeOne)
            in 35.01f..40.0f -> context.getString(R.string.obesityGradeTwo)
            in 40.1f..1000000.0f -> context.getString(R.string.obesityGradeThree)
            else -> context.getString(R.string.bmiOutOfRange)
        }
        return bmiCategory
    }

    fun BMIColor(bmi: Float, context: Activity): Int{
        val bmiColor = when(bmi){
            0.0f -> context.getColor(R.color.black)
            in 0.01f..16.0f -> context.getColor(R.color.colorSevereUnderweight)
            in 16.0f..17.0f -> context.getColor(R.color.colorModerateUnderweight)
            in 17.01f..18.5f -> context.getColor(R.color.colorSlightUnderweight)
            in 18.51f..25.0f -> context.getColor(R.color.colorNormalWeight)
            in 25.01f..30.0f -> context.getColor(R.color.colorPreadiposity)
            in 30.01f..35.0f -> context.getColor(R.color.colorObesityGradeOne)
            in 35.01f..40.0f -> context.getColor(R.color.colorObesityGradeTwo)
            in 40.1f..1000000.0f -> context.getColor(R.color.colorObesityGradeThree)
            else -> context.getColor(R.color.black)
        }
        return bmiColor
    }

    fun EnergyNeedCalculator(weight: Float, workNeedPerKgAndHourInKj: Float, workHoursADay: Float, hobbyNeedPerKgAndHourInKj: Float, hobbyHoursADay: Float){
        // Grundumsatz (GU): 4 kJ x Gewicht in kg x 24h
        // leichte Arbeit (2-4 kJ)
        // TODO: lookup a value for Freizeitpauschale and work and free time activity categories
        val baseNeedPerKgAndHourInKj = 4.0
        val hoursADay = 24
        val baseNeed = baseNeedPerKgAndHourInKj * weight * hoursADay
        val workNeed = workNeedPerKgAndHourInKj * weight * workHoursADay
        val hobbyNeed = hobbyNeedPerKgAndHourInKj * weight * hobbyHoursADay
        // Gesamtenergiebedarf (GEB) = Grundumsatz (GU) + Leistungsumsatz (LU)
        val energyNeedPerDayInKj = baseNeed + workNeed + hobbyNeed
        //1 kcal = 4,1868 kJ
        val energyNeedPerDayInKc = energyNeedPerDayInKj/4.1868
    }
}