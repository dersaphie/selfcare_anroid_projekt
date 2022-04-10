package com.example.myroutine

import android.app.Activity
import android.content.res.Resources
import android.widget.EditText
import android.widget.Spinner
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(@PrimaryKey @ColumnInfo(name = "id") val id: Int,
                @ColumnInfo(name = "name") var name: String?,
                @ColumnInfo(name = "age") val age: Int?,
                @ColumnInfo(name = "weight") val weight: Float?,
                @ColumnInfo(name = "height") val height: Float?,
                @ColumnInfo(name = "sex") val sex: String? = Resources.getSystem().getString(R.string.pickYourSex),
                @ColumnInfo(name = "sleep_hours") val sleep_hours: Float?,
                @ColumnInfo(name = "sport_energy_need") val sport_energy_need: Float?,
                @ColumnInfo(name = "sport_hours") val sport_hours: Float?,
                @ColumnInfo(name = "work_energy_need") val work_energy_need: Float?,
                @ColumnInfo(name = "work_hours") val work_hours: Float?
                ) {
    private var sleepHoursADay = 0.0f
    private var workPalValue = 0.0f
    private var workHoursADay = 0.0f
    private var sportPalValue = 0.0f
    private var sportHoursADay = 0.0f
    private var bmi = 0.0f
    private var bmiColor = 0
    private var bmiCategory = "none"
    private var dailyEnergyNeedKcal = 0.0f
    private var dailyEnergyNeedKj = 0.0f
    private var dailyEnergyKcalAndKjString = "0.0 kcal / 0.0 kj"
    private val calculations = BodyCalculations()
    private lateinit var db: UserRoomDatabase
    private lateinit var userDao: UserDao

    private fun checkIfTableAndUserExistInDB(){
        // create user in user table if it does not exist
        if (userDao.getAll().isEmpty()) {
            userDao.insertAll(this)
        }
    }
    private fun updateUserDataInDB(){
        // update user data in db
        userDao.updateUsers(this)
    }

    private fun readUserDataFromDbAndUpdateProfileViews(){
        // read user data from db
        val user = userDao.selectAllById(0)
        this.name = user.name
    }
}