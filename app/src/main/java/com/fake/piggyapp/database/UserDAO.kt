package com.fake.piggyapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userEntity: UserEntity): Long

    @Query("SELECT * FROM user")
    fun getAllUsers(): List<UserEntity>

    @Query("DELETE FROM user")
    fun deleteAllUsers()
}