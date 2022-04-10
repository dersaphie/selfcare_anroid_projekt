package com.example.myroutine

import android.app.Activity

class BodyCalculations(val context: Activity) {
    fun bmiCalculator(weight: Float, height: Float):Float{
        return "%.2f".format(weight / (height/100.0f * height/100.0f)).toFloat()
        // BMI tables https://www.foodspring.de/bmi#vorteil
    }

    fun bmiCategory(bmi: Float, context: Activity): String {
        val bmiCategory = when(bmi){
            0.0f -> context.getString(R.string.valuesForBMIAreIncomplete)
            in 0.01f..16.0f -> context.getString(R.string.severeUnderweight)
            in 16.01f..17.0f -> context.getString(R.string.moderateUnderweight)
            in 17.01f..18.5f -> context.getString(R.string.slightUnderweight)
            in 18.51f..25.0f -> context.getString(R.string.normalWeight)
            in 25.01f..30.0f -> context.getString(R.string.preadiposity)
            in 30.01f..35.0f -> context.getString(R.string.obesityGradeOne)
            in 35.01f..40.0f -> context.getString(R.string.obesityGradeTwo)
            in 40.1f..200.0f -> context.getString(R.string.obesityGradeThree)
            4.10070522e+12f -> context.getString(R.string.sun)
            else -> context.getString(R.string.bmiOutOfRange)
        }
        return bmiCategory
    }

    fun bmiColor(bmi: Float, context: Activity): Int{
        val bmiColor = when(bmi){
            0.0f -> context.getColor(R.color.black)
            in 0.01f..16.0f -> context.getColor(R.color.colorSevereUnderweight)
            in 16.0f..17.0f -> context.getColor(R.color.colorModerateUnderweight)
            in 17.01f..18.5f -> context.getColor(R.color.colorSlightUnderweight)
            in 18.51f..25.0f -> context.getColor(R.color.colorNormalWeight)
            in 25.01f..30.0f -> context.getColor(R.color.colorPreadiposity)
            in 30.01f..35.0f -> context.getColor(R.color.colorObesityGradeOne)
            in 35.01f..40.0f -> context.getColor(R.color.colorObesityGradeTwo)
            in 40.1f..200.0f -> context.getColor(R.color.colorObesityGradeThree)
            4.10070522e+12f -> context.getColor(R.color.sun)
            else -> context.getColor(R.color.black)
        }
        return bmiColor
    }

    fun energyNeedCalculatorKcal(weight: Float, height: Float, sex: String, age: Int, sleepHoursADay: Float, workPalValue: Float, workHoursADay: Float, sportPalValue: Float, sportHoursADay: Float):Float{
        // Grundumsatz (GU): 4 kJ x Gewicht in kg x 24h
        // leichte Arbeit (2-4 kJ)
        val energyNeedPerDayInKcal: Float
        val sleepPalValue = 0.95f
        val awakeWithoutSportAndWorkPalValue = 1.6f
            if(sleepHoursADay in 0f..24f && sportHoursADay in 0f..24f && workHoursADay in 0f..24f)
        {
            val awakeHoursWithoutSportAndWorkHours = 24.0f - sleepHoursADay - workHoursADay - sportHoursADay
            if(awakeHoursWithoutSportAndWorkHours > 0)
            {
                val combinedPalValue = ((sleepHoursADay * sleepPalValue) + (awakeHoursWithoutSportAndWorkHours * awakeWithoutSportAndWorkPalValue) + (workHoursADay * workPalValue) + (sportHoursADay * sportPalValue)) / 24
                val energyBaseNeedPerDayInKcal: Float = when (sex) {
                    context.getString(R.string.sexMan) -> {
                        // 66,47 + (13,7 × Körpergewicht in kg) + (5 × Körpergröße in cm) – (6,8 × Alter in Jahren) = Grundumsatz [kcal/24 h]
                        (66.47f + (13.7f * weight) + (5f * height) - (6.8f * age.toFloat()))
                    }
                    context.getString(R.string.sexWoman) -> {
                        // 655,1 + (9,6 × Körpergewicht in kg) + (1,8 × Körpergröße in cm) – (4,7 × Alter in Jahren) = Grundumsatz [kcal/24 h]
                        (655.1f + (9.6f * weight) + (1.8f * height) - (4.7f * age.toFloat()))
                    }
                    else -> {
                        throw IllegalArgumentException(context.getString(R.string.errorMessageNoEnergyNeedFormulaForSex)+": "+sex)
                    }
                }
                energyNeedPerDayInKcal = "%.2f".format(energyBaseNeedPerDayInKcal * combinedPalValue).toFloat()
            }else{
                throw IllegalArgumentException(context.getString(R.string.errorMessageSleepSportWorkHoursCombinedOver24Hours))
            }
        }else{
            throw IllegalArgumentException(context.getString(R.string.errorMessageSleepSportAndWorkHoursRange))
        }
        return energyNeedPerDayInKcal
    }

    fun energyNeedCalculatorKj(weight: Float, height: Float, sex: String, age: Int, sleepHoursADay: Float, workPalValue: Float, workHoursADay: Float, sportPalValue: Float, sportHoursADay: Float):Float{
        val kcalToKjFactor = 4.1868f
        return "%.2f".format(energyNeedCalculatorKcal(weight, height, sex, age, sleepHoursADay, workPalValue, workHoursADay, sportPalValue, sportHoursADay) * kcalToKjFactor).toFloat()
    }

    fun kcalToKjConverter(kcalValue: Float):Float{
        val kcalToKjFactor = 4.1868f
        return "%.2f".format(kcalValue * kcalToKjFactor).toFloat()
    }

    fun palCategoryToPalValueWork(palCategory: String, context: Activity): Float
    {
        val palValue = when(palCategory){
            context.getString(R.string.pickYourWorkEnergyNeed) -> 0.0f
            context.getString(R.string.palSleep) -> 0.95f
            context.getString(R.string.palJustSittingOrLayingActivity) -> 1.2f
            context.getString(R.string.palMostlySittingOrLayingActivity) -> 1.4f
            context.getString(R.string.palSomeWalkingActivity) -> 1.6f
            context.getString(R.string.palMostlyWalkingOrStandingActivity) -> 1.8f
            context.getString(R.string.palPhysicalDemandingActivity) -> 2.0f
            context.getString(R.string.palPhysicalVeryDemandingActivity) -> 2.4f
            else -> 0.0f
        }
        return palValue
    }

    fun palCategoryToPalValueSport(palCategory: String, context: Activity): Float
    {
        val palValue = when(palCategory){
            context.getString(R.string.pickYourSportEnergyNeed) -> 0.0f
            context.getString(R.string.palVeryLightSport) -> 1.6f
            context.getString(R.string.palLightSport) -> 1.8f
            context.getString(R.string.palModerateSport) -> 2.0f
            context.getString(R.string.palPhysicalDemandingSport) -> 2.4f
            context.getString(R.string.palPhysicalVeryDemandingSport) -> 2.7f
            context.getString(R.string.palCompetitiveSport) -> 3.0f
            else -> 0.0f
        }
        return palValue
    }

    fun palValueToPalCategoryWork(palValue: Float?, context: Activity): String
    {
        val palCategory = when(palValue){
            0.0f -> context.getString(R.string.pickYourWorkEnergyNeed)
            0.95f -> context.getString(R.string.palSleep)
            1.2f -> context.getString(R.string.palJustSittingOrLayingActivity)
            1.4f -> context.getString(R.string.palMostlySittingOrLayingActivity)
            1.6f -> context.getString(R.string.palSomeWalkingActivity)
            1.8f -> context.getString(R.string.palMostlyWalkingOrStandingActivity)
            2.0f -> context.getString(R.string.palPhysicalDemandingActivity)
            2.4f -> context.getString(R.string.palPhysicalVeryDemandingActivity)
            else -> context.getString(R.string.pickYourWorkEnergyNeed)
        }
        return palCategory
    }

    fun palValueToPalCategorySport(palValue: Float?, context: Activity): String
    {
        val palCategory = when(palValue){
            0.0f -> context.getString(R.string.pickYourSportEnergyNeed)
            1.6f -> context.getString(R.string.palVeryLightSport)
            1.8f -> context.getString(R.string.palLightSport)
            2.0f -> context.getString(R.string.palModerateSport)
            2.4f -> context.getString(R.string.palPhysicalDemandingSport)
            2.7f -> context.getString(R.string.palPhysicalVeryDemandingSport)
            3.0f -> context.getString(R.string.palCompetitiveSport)
            else -> context.getString(R.string.pickYourSportEnergyNeed)
        }
        return palCategory
    }
}