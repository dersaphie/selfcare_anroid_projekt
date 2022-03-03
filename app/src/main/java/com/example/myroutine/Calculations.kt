package com.example.myroutine

class Calculations {
    fun BMICalculator(weight: Float, height: Float){
        val bmi = weight / (height * height)
        // BMI tables https://www.foodspring.de/bmi#vorteil
    }

    fun BMICategory(bmi: Int): String {
        var bmiCategory = ""
        when(bmi){
            0-16 -> bmiCategory = "@string/severe_underweight"
            16-17 -> bmiCategory = "@string/moderate_underweight"
        }
        return bmiCategory
    }

    fun BMIColor(bmi: Int): String{
        var bmiColor = ""
        when(bmi){
            0-16 -> bmiColor = "@color/severe_underweight"
            16-17 -> bmiColor = "@color/moderate_underweight"
        }
        return bmiColor
    }

    fun EnergyNeedCalculator(){
        /*
        Gesamtenergiebedarf (GEB) = Grundumsatz (GU) + Leistungsumsatz (LU)
        Formel zur Berechnung des Energiebedarfs:

        Grundumsatz: 4 kJ x Gewicht in kg x 24h
        Leistungsumsatz: Tätigkeit in kJ x Gewicht in kg x Zeit in h (Arbeit und Sport müssen getrennt berechnet werden!) + Freizeitpauschale

        Gesamtenergiebedarf = Grundumsatz + Leistungsumsatz

        Beispielrechnung:

        Ich wiege beispielsweise 80kg und gehe einer leichten Arbeit nach (2-4 kJ) und betreibe Bodybuilding (2520 kJ/h). Dann würde die Formel so aussehen:

        Grundumsatz = 4 kJ x 80kg x 24h = 7680 kJ
        Leistungsumsatz = 3 kJ x 80kg x 8h + 2520 = 4440 kJ
        Gesamtenergiebedarf = 7680 kJ + 4440 kJ = 12120 kJ

        1 kcal = 4,1868 kJ das bedeutet für dieses Beispiel: 12120 kJ = 2895 Kcal
        */
    }
}