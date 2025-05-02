package com.fake.piggyapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fake.piggyapp.databinding.ActivityProfileBinding
import com.fake.piggyapp.database.UserEntity

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var user: UserEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
