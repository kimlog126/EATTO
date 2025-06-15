package com.example.eatto.intro

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.eatto.databinding.ActivityIntroSet1Binding

class IntroSet1 : AppCompatActivity() {

    private lateinit var binding: ActivityIntroSet1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroSet1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // "시작하기" 버튼 클릭 시 IntroSet2로 이동
        binding.start1.setOnClickListener {
            val intent = Intent(this, IntroSet2::class.java)
            startActivity(intent)
            finish()
        }
    }
}
