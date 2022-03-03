package com.example.myroutine

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context/*fragment: SportFragment*/) : SQLiteOpenHelper(context/*fragment.requireContext()*/, "MyRoutineDB", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        // WORKOUT table
        db?.execSQL("CREATE TABLE CARDIO(WORKOUTID INTEGER PRIMARY KEY AUTOINCREMENT, WKOUTNAME TEXT, WORKOUT TEXT)")
        db?.execSQL("INSERT INTO CARDIO(WKOUTNAME, WORKOUT) VALUES('el1', 'pwd1')")
        db?.execSQL("INSERT INTO CARDIO(WKOUTNAME, WORKOUT) VALUES('el2', 'pwd2')")
        // USER table
        db?.execSQL("CREATE TABLE USER(USERID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT NOT NULL, BDAY TEXT NOT NULL)")
        db?.execSQL("CREATE TABLE MESSUREMENTS(MESSUREID INTEGER PRIMARY KEY AUTOINCREMENT, USERID INTEGER NOT NULL, TIME INTEGER NOT NULL, WEIGHT INT NOT NULL, ARM_RIGHT INT NOT NULL, ARM_LEFT INT NOT NULL, BREAST INT NOT NULL, WAIST INT, HIPS INT NOT NULL, LEG_RIGHT INT NOT NULL, LEG_LEFT INT NOT NULL)")
    }
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}