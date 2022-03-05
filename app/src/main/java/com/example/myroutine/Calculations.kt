package com.example.myroutine

class Calculations {
    fun BMICalculator(weight: Float, height: Float):Float{
        return weight / (height * height)
        // BMI tables https://www.foodspring.de/bmi#vorteil
    }

    fun BMICategory(bmi: Int): String {
        var bmiCategory = ""
        when(bmi){
            0 -> bmiCategory = "@string/valuesForBMIAreIncomplete"
            1-16 -> bmiCategory = "@string/severeUnderweight"
            16-17 -> bmiCategory = "@string/moderateUnderweight"
        }
        return bmiCategory
    }

    fun BMIColor(bmi: Int): String{
        var bmiColor = ""
        when(bmi){
            0 -> bmiColor= "@color/colorValuesForBMIAreIncomplete"
            1-16 -> bmiColor = "@color/colorSevereUnderweight"
            16-17 -> bmiColor = "@color/colorModerateUnderweight"
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