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

        // 3초 후 로그인 상태에 따라 분기
        Handler(Looper.getMainLooper()).postDelayed({
            val user = FirebaseAuth.getInstance().currentUser
            if (user != null) {
                // 자동 로그인 유지 중
                startActivity(Intent(this, Home::class.java))
            } else {
                // 로그인 안 됨 → 회원가입 플로우 시작
                startActivity(Intent(this, IntroSet1::class.java))
            }
            finish()
        }, 3000)
    }
}
