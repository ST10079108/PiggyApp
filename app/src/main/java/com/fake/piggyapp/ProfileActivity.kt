package com.fake.piggyapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.fake.piggyapp.databinding.ActivityProfileBinding
import com.fake.piggyapp.database.UserEntity
import com.fake.piggyapp.databinding.ActivityCategoryHistoryBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var user: UserEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // View binding
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavView.setOnItemSelectedListener {
                item -> when (item.itemId) {
            R.id.itemStat -> {
                val intent = Intent(this, StatsActivity::class.java)
                startActivity(intent)
                true
            }
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
            R.id.itemProfile -> true

            else -> false
        }
        }

        user = JsonUtils.getUserFromPreferences(this)!!

        binding.txtUsername.text = user.username
        binding.txtPassword.text = "Password: ${user.password}"

        when (user.username) {
            "Lelo" -> binding.imageProfile.setImageResource(R.drawable.uicon1)
            "William" -> binding.imageProfile.setImageResource(R.drawable.uicon2)
            "Michel" -> binding.imageProfile.setImageResource(R.drawable.uicon3)

        }
    }
}
