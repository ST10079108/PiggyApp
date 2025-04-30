package com.fake.piggyapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var name: String
)
