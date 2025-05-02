package com.fake.piggyapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.fake.piggyapp.database.AppDatabase
import com.fake.piggyapp.database.UserDAO
import com.fake.piggyapp.database.UserEntity
import com.fake.piggyapp.database.CategoryDAO
import com.fake.piggyapp.database.CategoryEntity
import com.fake.piggyapp.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userDAO: UserDAO
    private lateinit var categoryDAO: CategoryDAO
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize DB and DAO
        db = AppDatabase.getDatabase(this) as AppDatabase
        userDAO = db.userDAO()
        categoryDAO = db.categoryDAO()

        // Insert user data into DB
        insertUsers()
        // Insert category data into DB
        insertCategories()

        binding.btnSignIn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    // Insert hardcoded drinks into database
    private fun insertUsers() {
        val products = listOf(
            UserEntity(username = "Lelo", password = "L8"),
            UserEntity(username = "William", password = "W6"),
            UserEntity(username = "Michel", password = "M4"),
        )

        CoroutineScope(Dispatchers.IO).launch {
            products.forEach { userDAO.insert(it) }
        }
    }

    private fun insertCategories() {
        val products = listOf(
            CategoryEntity(name = "Food"),
            CategoryEntity(name = "Groceries"),
            CategoryEntity(name = "Outings")

        )

        CoroutineScope(Dispatchers.IO).launch {
            products.forEach { categoryDAO.insert(it) }
        }
    }


}