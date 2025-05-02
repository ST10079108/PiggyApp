package com.fake.piggyapp

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.fake.piggyapp.database.AppDatabase
import com.fake.piggyapp.database.BudgetEntity
import com.fake.piggyapp.databinding.ActivityBudgetBinding
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class BudgetActivity : AppCompatActivity() {
    private lateinit var db: AppDatabase
    private lateinit var binding: ActivityBudgetBinding
    private var budget = BudgetEntity(0, "", "", 0.0, 0.0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityBudgetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //populate spinner with categories from database
        val spin: Spinner = findViewById(R.id.spCategory)
        val spinnerAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayListOf())
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin.adapter = spinnerAdapter
        db = AppDatabase.getDatabase(this) as AppDatabase
        lifecycleScope.launch {
            val categories = db.categoryDAO().getAllCategoryNames()
            spinnerAdapter.addAll(categories)
            spinnerAdapter.notifyDataSetChanged()
        }

        binding.btnAddBudget.setOnClickListener{
            //Get user input
            val bname = binding.etBudgetName.text.toString().trim()
            val bmin = binding.etMinimumAmount.text.toString().trim().toDoubleOrNull() ?: 0.0
            val bmax = binding.etMaximumAmount.text.toString().trim().toDoubleOrNull() ?: 0.0
            val cat = binding.etCategory.text.toString().trim()

            if (bname.isEmpty() || bmin == 0.0 || bmax == 0.0 || cat.isEmpty()) {
                Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            //Update the budget object
            budget.name = bname
            budget.min = bmin
            budget.max = bmax
            budget.category = cat

            // Save to SharedPreferences (optional)
            JsonUtils.saveBudgetToPreferences(this@BudgetActivity, budget)

            // Save to Room
            val db = AppDatabase.getDatabase(this) as AppDatabase
            val dao = db.budgetDAO()

            kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.IO).launch {
                budget.id = 0
                dao.insert(budget)
            }

            Toast.makeText(this, "Budget saved to history!", Toast.LENGTH_SHORT).show()
        }

        binding.btnMyBudgets.setOnClickListener {
            val intent = Intent(this, BudgetHistory::class.java)
            startActivity(intent)
        }
        binding.btnNewCategory.setOnClickListener {
            val intent = Intent(this, CategoryActivity::class.java)
            startActivity(intent)
        }
    }
}