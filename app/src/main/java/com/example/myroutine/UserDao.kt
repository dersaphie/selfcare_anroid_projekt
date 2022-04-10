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
    suspend fun insertUsers(vararg users: User)

    // update user
    @WorkerThread
    @Update
    fun updateUsers(vararg users: User)

    // get everything from user by id
    @WorkerThread
    @Query("SELECT * FROM user WHERE id IS :id")
    fun selectAllById(id: Int): User

    @WorkerThread
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @WorkerThread
    @Insert
    fun insertAll(vararg users: User)

    // for development
    @WorkerThread
    @Delete
    fun delete(user: User)
}