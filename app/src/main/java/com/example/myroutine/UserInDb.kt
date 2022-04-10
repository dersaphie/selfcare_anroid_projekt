package com.example.myroutine

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserInDb(@PrimaryKey @ColumnInfo(name = "id") val id: Int,
                    @ColumnInfo(name = "name") var name: String?,
                    @ColumnInfo(name = "age") val age: Int?,
                    @ColumnInfo(name = "weight") val weight: Float?,
                    @ColumnInfo(name = "height") val height: Float?,
                    @ColumnInfo(name = "sex") val sex: String?,
                    @ColumnInfo(name = "sleep_hours") val sleep_hours: Float?,
                    @ColumnInfo(name = "sport_energy_need") val sport_energy_need: Float?,
                    @ColumnInfo(name = "sport_hours") val sport_hours: Float?,
                    @ColumnInfo(name = "work_energy_need") val work_energy_need: Float?,
                    @ColumnInfo(name = "work_hours") val work_hours: Float?
                ) {}