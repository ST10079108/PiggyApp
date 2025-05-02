package com.fake.piggyapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.fake.piggyapp.databinding.ActivityHomeBinding
import com.fake.piggyapp.databinding.ActivityStatsBinding

class StatsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStatsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // View binding
        binding = ActivityStatsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.BottomNavigationView.setOnItemSelectedListener {
                item -> when (item.itemId) {
            R.id.itemStat -> true
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
    }
}