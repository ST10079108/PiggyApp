package com.fake.piggyapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.fake.piggyapp.database.AppDatabase
import com.fake.piggyapp.database.TransactionDAO
import com.fake.piggyapp.databinding.ActivityTransactionHistoryBinding
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fake.piggyapp.database.BudgetDAO
import com.fake.piggyapp.databinding.ActivityBudgetHistoryBinding

class TransactionHistory : AppCompatActivity() {

    private lateinit var binding: ActivityTransactionHistoryBinding
    private lateinit var budgetDAO: TransactionDAO
    private lateinit var db: AppDatabase
    private lateinit var budgetAdapter: BudgetAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


    }
}