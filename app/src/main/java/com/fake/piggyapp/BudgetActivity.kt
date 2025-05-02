package com.fake.piggyapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.fake.piggyapp.database.AppDatabase
import com.fake.piggyapp.database.BudgetEntity
import com.fake.piggyapp.databinding.ActivityBudgetBinding
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.launch

class BudgetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBudgetBinding
    private var budget = BudgetEntity(0, "", "", 0.0, 0.0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityBudgetBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
    }
}