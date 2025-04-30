package com.fake.piggyapp

import android.content.Context
import com.fake.piggyapp.database.UserEntity
import com.fake.piggyapp.database.BudgetEntity
import com.fake.piggyapp.database.CategoryEntity
import com.fake.piggyapp.database.TransactionEntity
import com.google.gson.Gson

object JsonUtils {
    private val gson = Gson()

    // User
    fun userToJson(user: UserEntity): String {
        return gson.toJson(user)
    }

    fun jsonToUser(json: String): UserEntity {
        return gson.fromJson(json, UserEntity::class.java)
    }

    fun saveUserToPreferences(context: Context, user: UserEntity) {
        val jsonString = userToJson(user)
        val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("order_key", jsonString)
            apply()
        }
    }

    fun getUserFromPreferences(context: Context): UserEntity? {
        val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val jsonString = sharedPreferences.getString("order_key", null)
        return jsonString?.let { jsonToUser(it) }
    }





    // Budget
    fun budgetToJson(budget: BudgetEntity): String {
        return gson.toJson(budget)
    }

    fun jsonToBudget(json: String): BudgetEntity {
        return gson.fromJson(json, BudgetEntity::class.java)
    }

    fun saveUserToPreferences(context: Context, budget: BudgetEntity) {
        val jsonString = budgetToJson(budget)
        val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("order_key", jsonString)
            apply()
        }
    }

    fun getBudgetFromPreferences(context: Context): BudgetEntity? {
        val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val jsonString = sharedPreferences.getString("order_key", null)
        return jsonString?.let { jsonToBudget(it) }
    }
}