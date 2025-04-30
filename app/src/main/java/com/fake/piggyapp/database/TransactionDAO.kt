package com.fake.piggyapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TransactionDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(transactionEntity: TransactionEntity): Long

    @Query("SELECT * FROM transactions")
    fun getAllTransactions(): List<TransactionEntity>

    @Query("DELETE FROM transactions")
    fun deleteAllTransactions()
}