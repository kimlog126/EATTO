package com.example.eatto.intro

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.eatto.databinding.ActivityIntroBinding

class Intro : AppCompatActivity() {

    private lateinit var binding: ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 3초 후 다음 액티비티로 전환
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, IntroSet1::class.java)
            startActivity(intent)
            finish() // 인트로 액티비티는 종료
        }, 3000)
    }
}
