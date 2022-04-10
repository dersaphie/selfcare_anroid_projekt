package com.example.myroutine

import android.app.Activity

class User(val context: Activity) {
    private val id = 0
    var name: String = context.getString(R.string.standardUserName)
    var age: Int = 0
        set(value){
            field = if(value >= 0) value else throw IllegalArgumentException(context.getString(R.string.errorMessageAgeMinimum))
        }
    var weight: Float = 0.0f
        set(value){
            field = if(value >= 0f) value else throw IllegalArgumentException(context.getString(R.string.errorMessageWeightMinimum))
        }
    var height: Float = 0f
        set(value){
            field = if(value >= 0f) value else throw IllegalArgumentException(context.getString(R.string.errorMessageHeightMinimum))
        }
    var sex: String = context.getString(R.string.sexPlaceHolder)
    var sleepHoursADay: Float = 0.0f
        set(value){
            field = if(value in 0f..24f) value else throw IllegalArgumentException(context.getString(R.string.errorMessageSleepHoursADayRange))
            if(sleepHoursADay+workHoursADay+sportHoursADay>24)
            {
                throw IllegalArgumentException(context.getString(R.string.errorMessageSleepSportWorkHoursCombinedOver24Hours))
            }
        }
    var workPalValue: Float = 0.0f
        set(value){
            field = if(value >= 0f) value else throw IllegalArgumentException(context.getString(R.string.errorMessageWorkPalValueMinimum))
        }
    var workHoursADay: Float = 0.0f
        set(value){
            field = if(value in 0f..24f) value else throw IllegalArgumentException(context.getString(R.string.errorMessageWorkHoursADayRange))
            if(sleepHoursADay+workHoursADay+sportHoursADay>24)
            {
                throw IllegalArgumentException(context.getString(R.string.errorMessageSleepSportWorkHoursCombinedOver24Hours))
            }
        }
    var sportPalValue: Float = 0.0f
        set(value){
            field = if(value >= 0f) value else throw IllegalArgumentException(context.getString(R.string.errorMessageSportPalValueMinimum))
        }
    var sportHoursADay: Float = 0.0f
        set(value){
            field = if(value in 0f..24f) value else throw IllegalArgumentException(context.getString(R.string.errorMessageSportHoursADayRange))
            if(sleepHoursADay+workHoursADay+sportHoursADay>24)
            {
                throw IllegalArgumentException(context.getString(R.string.errorMessageSleepSportWorkHoursCombinedOver24Hours))
            }
        }
    var bmi: Float = 0.0f
        set(value){
            field = if(value >= 0f) value else throw IllegalArgumentException(context.getString(R.string.errorMessageBmiMinimum))
        }
    var bmiColor: Int = 0
    var bmiCategory: String = context.getString(R.string.bmiColorPlaceholder)
    var dailyEnergyNeedKcal: Float = 0.0f
        set(value){
            field = if(value >= 0f) value else throw IllegalArgumentException(context.getString(R.string.errorMessageDailyEnergyNeedKcal))
        }
    var dailyEnergyNeedKj: Float = 0.0f
        set(value){
            field = if(value >= 0f) value else throw IllegalArgumentException(context.getString(R.string.errorMessageDailyEnergyNeedKj))
        }
    var dailyEnergyKcalAndKjString: String = context.getString(R.string.dailyEnergyNeedKcalAndKjStringPlaceholder)
    private val db = UserRoomDatabase.getDatabase(context)
    private val userDao = db.userDao()

    fun checkIfTableAndUserExistInDB(){
        // delete user for development
        //userDao.delete(userDao.selectAllById(0))
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