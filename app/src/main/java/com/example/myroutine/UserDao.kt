package com.example.myroutine

import androidx.annotation.WorkerThread
import androidx.room.*

@Dao
interface UserDao {
    // overwrite user
    // maybe better than updating because it ensures that the user exists
    // but should not be necessary due the if the user exists in ProfileFragment
    // and there are not many fields to write
    //@Suppress("RedundantSuspendModifier")
    @WorkerThread
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(vararg userInDbs: UserInDb)

    // update user
    @WorkerThread
    @Update
    fun updateUsers(vararg userInDbs: UserInDb)

    // get everything from user by id
    @WorkerThread
    @Query("SELECT * FROM user WHERE id IS :id")
    fun selectAllById(id: Int): UserInDb

    @WorkerThread
    @Query("SELECT * FROM user")
    fun getAll(): List<UserInDb>

    @WorkerThread
    @Insert
    fun insertAll(vararg userInDbs: UserInDb)

    // for development
    @WorkerThread
    @Delete
    fun delete(userInDb: UserInDb)
}