package com.fake.piggyapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.fake.piggyapp.database.AppDatabase
import com.fake.piggyapp.database.CategoryDAO
import com.fake.piggyapp.databinding.ActivityCategoryHistoryBinding
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.fake.piggyapp.databinding.ActivityTransactionHistoryBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoryHistory : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryHistoryBinding
    private lateinit var categoryDAO: CategoryDAO
    private lateinit var db: AppDatabase
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // View binding
        binding = ActivityCategoryHistoryBinding.inflate(layoutInflater)
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

        binding = ActivityCategoryHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getDatabase(this) as AppDatabase
        categoryDAO = db.categoryDAO()

        categoryAdapter = CategoryAdapter(emptyList())
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = categoryAdapter

        loadCategories()

        binding.btnClearHistory.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                categoryDAO.deleteAllCategories()

                // Refresh UI after deletion
                val categories = categoryDAO.getAllCategories()
                withContext(Dispatchers.Main) {
                    categoryAdapter.updateCategories(categories)
                    Toast.makeText(this@CategoryHistory, "All categories deleted!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun loadCategories() {
        CoroutineScope(Dispatchers.IO).launch {
            val allCategories = categoryDAO.getAllCategories()

            // Filter only categories that have been filled in
            val completedCategories = allCategories.filter {
                it.name.isNotBlank()
            }

            runOnUiThread {
                categoryAdapter.updateCategories(completedCategories)
            }
        }
    }
}