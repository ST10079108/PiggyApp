package com.fake.piggyapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budget")
data class BudgetEntity(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var name: String,
    var category: String,
    var min: Double,
    var max: Double
)
