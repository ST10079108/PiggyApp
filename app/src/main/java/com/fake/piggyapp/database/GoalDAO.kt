package com.fake.piggyapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GoalDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(goalEntity: GoalEntity): Long

    @Query("SELECT * FROM goal")
    fun getAllGoals(): List<GoalEntity>

    @Query("DELETE FROM goal")
    fun deleteAllGoals()
}