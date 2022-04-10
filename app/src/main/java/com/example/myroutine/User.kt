package com.example.myroutine

import android.app.Activity
import android.content.Context

class User(context: Context) {
    private val id = 0
    var name: String = "user"
    var age: Int = 0
    var weight: Float = 0.0f
    var height: Float = 0f
    var sex: String = "none"
    var sleepHoursADay: Float = 0.0f
    var workPalValue: Float = 0.0f
    var workHoursADay: Float = 0.0f
    var sportPalValue: Float = 0.0f
    var sportHoursADay: Float = 0.0f
    var bmi: Float = 0.0f
    var bmiColor: Int = 0
    var bmiCategory: String = "none"
    var dailyEnergyNeedKcal: Float = 0.0f
    var dailyEnergyNeedKj: Float = 0.0f
    var dailyEnergyKcalAndKjString: String = "0.0 kcal / 0.0 kj"
    private val db = UserRoomDatabase.getDatabase(context)
    private val userDao = db.userDao()

    fun checkIfTableAndUserExistInDB(){
        // create user in user table if it does not exist
        if (userDao.getAll().isEmpty()) {
            userDao.insertAll(UserInDb(id=id,name=name,age=age,weight=weight,height=height,sex=sex, sleep_hours = sleepHoursADay, sport_energy_need = sportPalValue, sport_hours = sportHoursADay, work_energy_need = workPalValue, work_hours = workHoursADay))
        }
    }
    fun updateUserDataInDB(){
        // update user data in db
        userDao.updateUsers(UserInDb(id=id,name=name,age=age,weight=weight,height=height,sex=sex, sleep_hours = sleepHoursADay, sport_energy_need = sportPalValue, sport_hours = sportHoursADay, work_energy_need = workPalValue, work_hours = workHoursADay))
    }

    fun readUserDataFromDb(){
        // read user data from db
        val user = userDao.selectAllById(0)
        this.name = user.name.toString()
        this.age = user.age!!
        this.weight = user.weight!!
        this.height = user.height!!
        this.sex = user.sex!!
        this.sleepHoursADay = user.sleep_hours!!
        this.sportPalValue = user.sport_energy_need!!
        this.sportHoursADay = user.sport_hours!!
        this.workPalValue = user.work_energy_need!!
        this.workHoursADay = user.work_hours!!
    }
}