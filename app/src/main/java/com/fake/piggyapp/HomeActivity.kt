package com.fake.piggyapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.fake.piggyapp.database.UserEntity
import com.fake.piggyapp.database.AppDatabase
import com.fake.piggyapp.database.BudgetEntity
import com.fake.piggyapp.database.GoalEntity
import com.fake.piggyapp.database.UserDAO
import com.fake.piggyapp.databinding.ActivityHomeBinding
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {


    private var goal = GoalEntity(0, 0.0, 0.0)
    private lateinit var binding: ActivityHomeBinding
    private var user = UserEntity(0, "", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // View binding
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.BottomNavigationView.setOnItemSelectedListener {
            item -> when (item.itemId) {
                R.id.itemStat -> {
                    val intent = Intent(this, StatsActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.itemTrans -> {
                    val intent = Intent(this, AddTransactionActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.itemHome -> true
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

        val userFromPrefs = JsonUtils.getUserFromPreferences(this)
        userFromPrefs?.let { user = it }

        binding.tvUsername.text = "Welcome ${user.username}!"

        when (user.username) {
            "Lelo" -> binding.ivUserImage.setImageResource(R.drawable.uicon1)
            "William" -> binding.ivUserImage.setImageResource(R.drawable.uicon2)
            "Michel" -> binding.ivUserImage.setImageResource(R.drawable.uicon3)
        }

        binding.btnSaveGoal.setOnClickListener{
            //Get user input
            val gmin = binding.etMin.text.toString().trim().toDoubleOrNull() ?: 0.0
            val gmax = binding.etMax.text.toString().trim().toDoubleOrNull() ?: 0.0

            if ( gmin == 0.0 || gmax == 0.0) {
                Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            //Update the budget object
            goal.min = gmin
            goal.max = gmax

            // Save to SharedPreferences (optional)
//            JsonUtils.saveGoalToPreferences(this@HomeActivity, goal)

            // Save to Room
            val db = AppDatabase.getDatabase(this) as AppDatabase
            val dao = db.goalDAO()

            kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.IO).launch {
                goal.id = 0
                dao.insert(goal)
            }

            Toast.makeText(this, "Goal saved to history!", Toast.LENGTH_SHORT).show()
        }
    }
}