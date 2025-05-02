package com.fake.piggyapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fake.piggyapp.database.AppDatabase
import com.fake.piggyapp.database.UserDAO
import com.fake.piggyapp.databinding.ActivityTransactionBinding

class transactionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTransactionBinding
    private lateinit var userDAO: UserDAO
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // View Binding
        binding = ActivityTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize DB and DAO
        db = AppDatabase.getDatabase(this) as AppDatabase
        userDAO = db.userDAO()

        binding.btnAddTransactionPage.setOnClickListener {
            val intent = Intent(this, AddTransactionActivity::class.java)
            startActivity(intent)
        }

    }
}