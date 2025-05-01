package com.fake.piggyapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.fake.piggyapp.database.AppDatabase
import com.fake.piggyapp.database.UserDAO
import com.fake.piggyapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var userDAO: UserDAO
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // View binding
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavView.setOnItemSelectedListener {
            item -> when (item.itemId) {
                //R.id.itemStat -> {
            //                    val intent = Intent(this, StatisticActivity::class.java)
            //                    startActivity(intent)
            //                    true
            //                }
                //R.id.itemTrans -> {
            //                    val intent = Intent(this, TransactionActivity::class.java)
            //                    startActivity(intent)
            //                    true
            //                }
                R.id.itemHome -> true
                R.id.itemBudget -> {
                    val intent = Intent(this, BudgetActivity::class.java)
                    startActivity(intent)
                    true
                }
                //R.id.itemProfile -> {
            //                    val intent = Intent(this, ProfileActivity::class.java)
            //                    startActivity(intent)
            //                    true
            //                }

            else -> false
            }
        }

    }
}