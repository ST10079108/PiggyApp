package com.fake.piggyapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.fake.piggyapp.database.AppDatabase
import com.fake.piggyapp.database.TransactionDAO
import com.fake.piggyapp.databinding.ActivityTransactionHistoryBinding
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.fake.piggyapp.database.BudgetDAO
import com.fake.piggyapp.databinding.ActivityBudgetHistoryBinding
import com.fake.piggyapp.databinding.ActivityHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TransactionHistory : AppCompatActivity() {

    private lateinit var binding: ActivityTransactionHistoryBinding
    private lateinit var transactionDAO: TransactionDAO
    private lateinit var db: AppDatabase
    private lateinit var transactionAdapter: TransactionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // View binding
        binding = ActivityTransactionHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.BottomNavigationView.setOnItemSelectedListener {
                item -> when (item.itemId) {
            R.id.itemStat -> {
                val intent = Intent(this, StatsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.itemTrans -> true
            R.id.itemHome -> {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.itemBudget -> {
                val intent = Intent(this, BudgetActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.itemProfile -> {
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
                true
            }

            else -> false
        }
        }



        db = AppDatabase.getDatabase(this) as AppDatabase
        transactionDAO = db.transactionDAO()

        transactionAdapter = TransactionAdapter(emptyList())
        binding.recyclerViewTransaction.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewTransaction.adapter = transactionAdapter


        loadTransactions()

        binding.btnClearHistory.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                transactionDAO.deleteAllTransactions()

                // Refresh UI after deletion
                val transactions = transactionDAO.getAllTransactions()
                withContext(Dispatchers.Main) {
                    transactionAdapter.updateTransactions(transactions)
                    Toast.makeText(this@TransactionHistory, "All transactions deleted!", Toast.LENGTH_SHORT).show()
                }
            }
        }


    }

    private fun loadTransactions() {
        CoroutineScope(Dispatchers.IO).launch {
            val allTransactions = transactionDAO.getAllTransactions()

            // Filter only transactions that have been filled in
            val completedTransactions = allTransactions.filter {
               it.type.isNotBlank() &&
                       it.amount != 0.0 &&
                       it.date.isNotBlank() &&
                       it.category.isNotBlank() &&
                       it.recurringType.isNotBlank()
            }

            runOnUiThread {
                transactionAdapter.updateTransactions(completedTransactions)
            }
        }
    }

}