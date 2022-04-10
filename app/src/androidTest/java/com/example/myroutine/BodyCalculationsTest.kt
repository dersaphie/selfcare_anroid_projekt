package com.example.myroutine

import junit.framework.TestCase
import org.junit.Test

class BodyCalculationsTest : TestCase() {
    private val bodyCalculations = BodyCalculations(this@MainActivity)
    @Test
    fun testBmiCalculator()
    {
        val expected = 24.85
        assertEquals(expected, bodyCalculations.bmiCalculator(80.5f, 180f))
    }

    fun testKcalToKjConverter()
    {
        val expected = 8373.6f
        assertEquals(expected, bodyCalculations.kcalToKjConverter(2000f))
    }
}