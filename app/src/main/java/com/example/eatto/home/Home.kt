package com.example.eatto.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.eatto.R
import com.example.eatto.community.Community
import com.example.eatto.databinding.ActivityHomeBinding
import com.example.eatto.more.More
import com.example.eatto.report.Report

class Home : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewBinding 연결
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // BottomNavigationView 클릭 처리
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // 현재 화면이므로 이동 X
                    true
                }
                R.id.nav_report -> {
                    startActivity(Intent(this, Report::class.java))
                    true
                }
                R.id.nav_community -> {
                    startActivity(Intent(this, Community::class.java))
                    true
                }
                R.id.nav_more -> {
                    startActivity(Intent(this, More::class.java))
                    true
                }
                else -> false
            }
        }

        // 예시: 우측 이미지 버튼 클릭 이벤트 (챌린지 섹션의 화살표 버튼 등)
        binding.imageButton.setOnClickListener {
            // 원하는 동작 추가
        }
    }
}
