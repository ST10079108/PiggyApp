package com.fake.piggyapp

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fake.piggyapp.database.AppDatabase
import com.fake.piggyapp.database.BudgetEntity
import com.fake.piggyapp.databinding.ActivityAddBudgetBinding
import com.fake.piggyapp.databinding.ActivityBudgetBinding
import kotlinx.coroutines.launch

class AddBudgetActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBudgetBinding
    private var budget = BudgetEntity(0, "", "", 0.0, 0.0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAddBudgetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBudgetAdd.setOnClickListener {
            //Get user input
            val bname = binding.etBudgetName.text.toString().trim()
            val bamount = binding.etBudgetAmount.text.toString().trim().toDoubleOrNull() ?: 0.0
            val bcat = binding.spnCategory.selectedItem.toString().trim()

            if (bname.isEmpty() || bamount == 0.0 || bcat.isEmpty()) {
                Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            //Update the budget object
            budget.name = bname
            budget.min = bamount
            budget.category = bcat

            // Save to SharedPreferences (optional)
            JsonUtils.saveBudgetToPreferences(this@AddBudgetActivity, budget)

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