package com.fake.piggyapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.fake.piggyapp.database.UserEntity
import com.fake.piggyapp.database.AppDatabase
import com.fake.piggyapp.database.UserDAO
import com.fake.piggyapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private var user = UserEntity(0, "", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // View binding
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavView.setOnItemSelectedListener {
            item -> when (item.itemId) {
                R.id.itemStat -> {
                    val intent = Intent(this, StatsActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.itemTrans -> {
                    val intent = Intent(this, transactionActivity::class.java)
                    startActivity(intent)
                    true
                }
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

        val userFromPrefs = JsonUtils.getUserFromPreferences(this)
        userFromPrefs?.let { user = it }

        binding.tvUsername.text = "Welcome ${user.username}!"

        when (user.username) {
            "Lelo" -> binding.ivUserImage.setImageResource(R.drawable.uicon1)
            "William" -> binding.ivUserImage.setImageResource(R.drawable.uicon2)
            "Michel" -> binding.ivUserImage.setImageResource(R.drawable.uicon3)
        }
    }
}