package com.fake.piggyapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.fake.piggyapp.database.AppDatabase
import com.fake.piggyapp.database.TransactionEntity
import com.fake.piggyapp.databinding.ActivityTransactionHistoryBinding
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.fake.piggyapp.database.BudgetEntity
import com.fake.piggyapp.databinding.ActivityAddTransactionBinding
import com.fake.piggyapp.databinding.ActivityBudgetBinding
import kotlinx.coroutines.launch

class AddTransactionActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var db: AppDatabase
    private lateinit var binding: ActivityAddTransactionBinding
    private var transaction = TransactionEntity(0, "", 0.0, "", "", "", "")

    private var types = arrayOf(
        "Income", "Expense"
    )
    private var recurrences = arrayOf(
        "Monthly", "Weekly"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

// types spinner
        val spin = findViewById<Spinner>(R.id.spType)
        spin.onItemSelectedListener = this
        val ad: ArrayAdapter<*> = ArrayAdapter<Any?>(this,
            android.R.layout.simple_spinner_item, types
        )
        ad.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        spin.adapter = ad
// recurrences spinner
        val recSpin = findViewById<Spinner>(R.id.spRecurrence)
        recSpin.onItemSelectedListener = this
        val ada: ArrayAdapter<*> = ArrayAdapter<Any?>(this,
            android.R.layout.simple_spinner_item, recurrences
        )
        ada.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        recSpin.adapter = ada
// category spinner
        //populate spinner with categories from database
        val cspin: Spinner = findViewById(R.id.spCategory)
        val sAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayListOf())
        sAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        cspin.adapter = sAdapter
        db = AppDatabase.getDatabase(this) as AppDatabase
        lifecycleScope.launch {
            val categories = db.categoryDAO().getAllCategoryNames()
            sAdapter.addAll(categories)
            sAdapter.notifyDataSetChanged()
        }

        binding.btnAddTransaction.setOnClickListener{
            //Get user input
            var tType = binding.spType.selectedItem.toString().trim()
            var tAmount = binding.etTransactionAmount.text.toString().trim().toDoubleOrNull() ?: 0.0
            var tDate = binding.etDate.text.toString().trim()
            var tCategory = binding.spCategory.selectedItem.toString().trim()
            var tDescription = binding.etDescription.text.toString().trim()
            var tRecurringType = binding.spRecurrence.selectedItem.toString().trim()


            if (tType.isEmpty() || tAmount == 0.0 || tDate.isEmpty() || tCategory.isEmpty() || tDescription.isEmpty() ||tRecurringType.isEmpty() ) {
                Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            //Update the transaction object
            transaction.type = tType
            transaction.amount = tAmount
            transaction.date = tDate
            transaction.category = tCategory
            transaction.description = tDescription
            transaction.recurringType = tRecurringType

            // Save to SharedPreferences (optional)
            JsonUtils.saveTransactionToPreferences(this@AddTransactionActivity, transaction)

            // Save to Room
            val db = AppDatabase.getDatabase(this) as AppDatabase
            val dao = db.transactionDAO()

            kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.IO).launch {
                transaction.id = 0
                dao.insert(transaction)
            }

            Toast.makeText(this, "Transaction saved to history!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, TransactionHistory::class.java)
            startActivity(intent)
        }

        binding.btnNewCategory.setOnClickListener {
            val intent = Intent(this, CategoryActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent?.id) {
            R.id.spType -> Toast.makeText(this, types[position], Toast.LENGTH_SHORT).show()
            R.id.spRecurrence -> Toast.makeText(this, recurrences[position], Toast.LENGTH_SHORT).show()
            R.id.spCategory -> Toast.makeText(this, parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
}