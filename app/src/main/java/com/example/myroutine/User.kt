package com.example.myroutine

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(@PrimaryKey @ColumnInfo(name = "userid") val userid: Int, @ColumnInfo(name = "name") val name: String?) {
    //companion object {
    //    var currentUser: User? = null
    //}
}