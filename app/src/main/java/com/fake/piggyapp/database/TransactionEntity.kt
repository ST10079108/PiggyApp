package com.fake.piggyapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var type: String,
    var amount: Double,
    var date: String,
    var category: String,
    var description: String,
    var recurringType: String,
    var image: ByteArray? = null,
    var userId: Int

)
