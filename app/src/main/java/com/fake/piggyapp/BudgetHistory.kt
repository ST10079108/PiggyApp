package com.fake.piggyapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.fake.piggyapp.database.AppDatabase
import com.fake.piggyapp.database.BudgetDAO
import com.fake.piggyapp.databinding.ActivityBudgetHistoryBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class BudgetHistory : AppCompatActivity() {

    private lateinit var binding: ActivityBudgetHistoryBinding
    private lateinit var budgetDAO: BudgetDAO
    private lateinit var db: AppDatabase
    private lateinit var budgetAdapter: BudgetAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBudgetHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getDatabase(this) as AppDatabase
        budgetDAO = db.budgetDAO()

        budgetAdapter = BudgetAdapter(emptyList())
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = budgetAdapter

        loadBudgets()

        binding.btnClearHistory.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                budgetDAO.deleteAllBudgets()

                // Refresh UI after deletion
                val budgets = budgetDAO.getAllBudgets()
                withContext(Dispatchers.Main) {
                    budgetAdapter.updateBudgets(budgets)
                    Toast.makeText(this@BudgetHistory, "All orders deleted!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun loadBudgets() {
        CoroutineScope(Dispatchers.IO).launch {
            val allBudgets = budgetDAO.getAllBudgets()

            // Filter only budgets that have been filled in
            val completedBudgets = allBudgets.filter {
                it.name.isNotBlank() &&
                        it.category.isNotBlank() &&
                        it.min != 0.0 &&
                        it.max != 0.0
            }

            runOnUiThread {
                budgetAdapter.updateBudgets(completedBudgets)
            }
        }
    }
}