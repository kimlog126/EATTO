package com.example.eatto.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.eatto.R
import com.example.eatto.community.Challenge
import com.example.eatto.community.Community
import com.example.eatto.databinding.ActivityHomeBinding
import com.example.eatto.intro.IntroSet2
import com.example.eatto.more.More
import com.example.eatto.report.Report
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Home : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewBinding 연결
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ✅ Firestore에서 사용자 정보 불러오기
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid != null) {
            FirebaseFirestore.getInstance().collection("users").document(uid)
                .get()
                .addOnSuccessListener { document ->
                    val goalKcal = document.getLong("goalKcal")?.toInt() ?: 0
                    val nickname = document.getString("nickname") ?: "회원"

                    binding.goalKcal.text = "${goalKcal}\n권장"
                    binding.hello.text = "안녕하세요, ${nickname}님!"
                }
                .addOnFailureListener {
                    binding.goalKcal.text = "정보 없음"
                    binding.hello.text = "안녕하세요!"
                }
        }

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

        // 화살표 버튼 클릭 → Challenge로 이동
        binding.imageButton.setOnClickListener {
            val intent = Intent(this, Challenge::class.java)
            startActivity(intent)
            finish()
        }
    }
}
