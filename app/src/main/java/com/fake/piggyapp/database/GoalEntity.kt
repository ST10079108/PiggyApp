package com.fake.piggyapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "goal")
data class GoalEntity(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var min: Double,
    var max: Double
)
