package com.fake.piggyapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var type: String, //income or expense
    var amount: Double,
    var date: String, // dd/mm/yy
    var category: String, //groceries, entertainment, salary, rent
    var description: String,
    var recurringType: String, // no, weekly, monthly, yearly
    var image: ByteArray? = null

)
