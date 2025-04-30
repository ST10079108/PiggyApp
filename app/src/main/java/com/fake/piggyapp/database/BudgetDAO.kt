package com.fake.piggyapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BudgetDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(budgetEntity: BudgetEntity): Long

    @Query("SELECT * FROM budget")
    fun getAllBudgets(): List<BudgetEntity>

    @Query("DELETE FROM budget")
    fun deleteAllBudgets()
}