package com.example.myroutine

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper (context: Context,/*fragment: SportFragment*/) : SQLiteOpenHelper (context/*fragment.requireContext()*/, "MyRoutineDB", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE CARDIO(WORKOUTID INTEGER PRIMARY KEY AUTOINCREMENT, WKOUTNAME TEXT, WORKOUT TEXT)")
        db?.execSQL("INSERT INTO CARDIO(WKOUTNAME, WORKOUT) VALUES('el1', 'pwd1')")
        db?.execSQL("INSERT INTO USERS(WKOUTNAME, WORKOUT) VALUES('el2', 'pwd2')")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}