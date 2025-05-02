package com.fake.piggyapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.fake.piggyapp.database.AppDatabase
import com.fake.piggyapp.database.CategoryEntity
import com.fake.piggyapp.databinding.ActivityCategoryBinding
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.launch

class CategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryBinding
    private var category = CategoryEntity(0, "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddCategory.setOnClickListener {
            /// Get user input
            val cname = binding.etCategoryName.text.toString().trim()


            if (cname.isEmpty()) {
                Toast.makeText(this, "Please enter category name", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            // Set the current date
            val date = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
                .format(java.util.Date())

            // Update the category object
            category.name = cname


            // Save to SharedPreferences (optional)
            JsonUtils.saveCategoryToPreferences(this@CategoryActivity, category)

            // Save to Room
            val db = AppDatabase.getDatabase(this) as AppDatabase
            val dao = db.categoryDAO()

            kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.IO).launch {
                category.id = 0
                dao.insert(category)
            }

            Toast.makeText(this, "Category saved to history!", Toast.LENGTH_SHORT).show()
        }

        binding.btnMyCategories.setOnClickListener {
            val intent = Intent(this, CategoryHistory::class.java)
            startActivity(intent)
        }
        binding.btnAddCategory.setOnClickListener {
            Toast.makeText(this, "Category saved to history!", Toast.LENGTH_SHORT).show()
        }
    }
}