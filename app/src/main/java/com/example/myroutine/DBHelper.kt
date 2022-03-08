package com.example.myroutine

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context/*fragment: SportFragment*/) : SQLiteOpenHelper(context/*fragment.requireContext()*/, "MyRoutineDB", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        // USER table
        db?.execSQL("CREATE TABLE USER(USERID INTEGER PRIMARY KEY, NAME TEXT, AGE FLOAT, WEIGHT FLOAT, HEIGHT FLOAT, SEX FLOAT, SLEEP_HOURS_A_DAY FLOAT, WORK_HOURS_A_DAY FLOAT, WORK_PAL_VALUE FLOAT, SPORT_HOURS FLOAT_A_DAY, SPORT_PAL_VALUE FLOAT, BMI FLOAT, DAILY_ENERGY_NEED_KCAL FLOAT)")
        db?.execSQL("INSERT INTO USER(USERID,NAME,AGE,WEIGHT,HEIGHT,SEX,SLEEP_HOURS_A_DAY,WORK_HOURS_A_DAY,WORK_PAL_VALUE,SPORT_HOURS,SPORT_PAL_VALUE,BMI,DAILY_ENERGY_NEED_KCAL) VALUES(0,'user',0,0,0,'Pick your sex',0,0,0,0,0,0,0)")
    }
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}