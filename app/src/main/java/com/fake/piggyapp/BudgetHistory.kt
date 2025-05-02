package com.fake.piggyapp

import android.content.Intent
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
import com.fake.piggyapp.databinding.ActivityCategoryHistoryBinding

class BudgetHistory : AppCompatActivity() {

    private lateinit var binding: ActivityBudgetHistoryBinding
    private lateinit var budgetDAO: BudgetDAO
    private lateinit var db: AppDatabase
    private lateinit var budgetAdapter: BudgetAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // View binding
        binding = ActivityBudgetHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.BottomNavView.setOnItemSelectedListener {
                item -> when (item.itemId) {
            R.id.itemStat -> {
                val intent = Intent(this, StatsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.itemTrans -> {
                val intent = Intent(this, TransactionHistory::class.java)
                startActivity(intent)
                true
            }
            R.id.itemHome -> {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.itemBudget -> true
            R.id.itemProfile -> {
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
                true
            }

            else -> false
        }
        }

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
                    Toast.makeText(this@BudgetHistory, "All budgets deleted!", Toast.LENGTH_SHORT).show()
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