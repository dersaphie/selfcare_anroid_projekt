package com.example.myroutine

import android.app.ActivityManager
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

abstract class ThemeChange : AppCompatActivity(){
    private var currentTheme = TEAL

    // on create the current Theme is set
    override fun onCreate(savedInstanceState: Bundle?) {
        //change sharedPref https://stackoverflow.com/questions/10786172/android-getdefaultsharedpreferences
        val sharedPrefs = getSharedPreferences("NAME", MODE_PRIVATE)
        currentTheme = sharedPrefs.getInt(KEY_THEME, TEAL)
        //currentTheme = PreferenceManager.getDefaultSharedPreferences(this).getInt(KEY_THEME, TEAL)
        super.onCreate(savedInstanceState)
    }

    protected fun setTheme() {
        setTheme(currentTheme)
    }
    protected fun switchTheme() {
        currentTheme = when(currentTheme) {
            TEAL -> CYAN
            CYAN -> TEAL
            else -> -1
        }

        //getSharedPreferences("test").edit().putInt(KEY_THEME, currentTheme).apply()
        val sharedPrefs = getSharedPreferences("NAME", MODE_PRIVATE)
        val editor = sharedPrefs.edit().putInt(KEY_THEME, currentTheme).apply()
        setTheme(currentTheme)
        recreate()
    }

    private fun getColorPrimary() = when(currentTheme) {
        TEAL -> R.color.colorPrimaryTeal
        CYAN -> R.color.colorPrimaryCyan
        else -> android.R.color.background_light
    }

    companion object {
        private const val KEY_THEME = "Theme"
        private const val TEAL = R.style.AppTheme_Teal
        private const val CYAN = R.style.AppTheme_Cyan
    }
}