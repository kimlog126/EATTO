package com.example.eatto.intro

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.eatto.databinding.ActivityIntroBinding
import com.example.eatto.home.Home
import com.google.firebase.auth.FirebaseAuth

class Intro : AppCompatActivity() {

    private lateinit var binding: ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            val user = FirebaseAuth.getInstance().currentUser
            if (user != null) {
                startActivity(Intent(this, Home::class.java))
            } else {
                startActivity(Intent(this, IntroSet1::class.java))
            }
            finish()
        }, 3000)
    }
}
