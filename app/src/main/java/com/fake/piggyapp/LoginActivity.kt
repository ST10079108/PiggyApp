package com.fake.piggyapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fake.piggyapp.database.AppDatabase
import com.fake.piggyapp.database.UserDAO
import com.fake.piggyapp.databinding.ActivityLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var userDAO: UserDAO
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // View Binding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize DB and DAO
        db = AppDatabase.getDatabase(this) as AppDatabase
        userDAO = db.userDAO()

        // Set up click listeners
        binding.btnLogin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val username = binding.edtUsername.text.toString()
        val password = binding.edtPassword.text.toString()
        when (v?.id) {
            R.id.btnLogin -> getUserFromDB(username, password)
        }
    }

    // Fetch product from DB, show Toast, open next activity
    private fun getUserFromDB(username: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = userDAO.getAllUsers().firstOrNull { it.username == username && it.password == password }

            user?.let {
                // Save to SharedPreferences as JSON
                JsonUtils.saveUserToPreferences(this@LoginActivity, it)

                withContext(Dispatchers.Main) {
                    Toast.makeText(this@LoginActivity, "Welcome ${it.username}", Toast.LENGTH_SHORT).show()
                    openIntent(this@LoginActivity, it.username, HomeActivity::class.java)
                }
            }
        }
    }
}