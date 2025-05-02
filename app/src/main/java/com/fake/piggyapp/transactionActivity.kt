package com.fake.piggyapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.fake.piggyapp.database.AppDatabase
import com.fake.piggyapp.database.UserDAO
import com.fake.piggyapp.database.BudgetDAO
import com.fake.piggyapp.database.TransactionDAO
import com.fake.piggyapp.databinding.ActivityTransactionBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class transactionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTransactionBinding
    private lateinit var userDAO: UserDAO
    private lateinit var transDAO: TransactionDAO
    private lateinit var db: AppDatabase
    //private lateinit var budgetAdapter: TransactionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // View Binding
        binding = ActivityTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize DB and DAO
        db = AppDatabase.getDatabase(this) as AppDatabase
        userDAO = db.userDAO()
       // transDAO = db.transDAO()

        binding.btnAddTransactionPage.setOnClickListener {
            val intent = Intent(this, AddTransactionActivity::class.java)
            startActivity(intent)
        }

        //binding.recyclerView.layoutManager = LinearLayoutManager(this)
        //binding.recyclerView.adapter = TransactionAdapter

      //  loadTransactions()

//        binding.btnClearHistory.setOnClickListener {
//            CoroutineScope(Dispatchers.IO).launch {
//                budgetDAO.deleteAllBudgets()
//
//                // Refresh UI after deletion
//                val budgets = transactionActivityDAO.getAllBudgets()
//                withContext(Dispatchers.Main) {
//                    budgetAdapter.updateBudgets(budgets)
//                    Toast.makeText(this@transactionActivity, "All transactions deleted!", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
    }

//    private fun loadTransactions() {
//        CoroutineScope(Dispatchers.IO).launch {
//            val allTransactions = TransactionDAO.getAllTransactions()
//
//            // Filter only budgets that have been filled in
//val completedBudgets = allTransactions.filter {
//                it.name.isNotBlank() &&
//                        it.category.isNotBlank() &&
 //                       it.min != 0.0 &&
   //                     it.max != 0.0
     //       }
//
 //           runOnUiThread {
  //              budgetAdapter.updateBudgets(completedBudgets)
    //        }
   //     }
   // }

 //   }
}