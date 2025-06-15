package com.example.eatto.intro

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.eatto.databinding.ActivityIntroSet2Binding
import com.example.eatto.home.Home
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class IntroSet2 : AppCompatActivity() {

    private lateinit var binding: ActivityIntroSet2Binding
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroSet2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.start2.setOnClickListener {
            val email = binding.inputEmail.text.toString()
            val password = binding.inputPassword.text.toString()
            val nickname = binding.inputName.text.toString()
            val height = binding.inputCm.text.toString().toFloatOrNull() ?: 0f
            val weight = binding.inputKg.text.toString().toFloatOrNull() ?: 0f
            val goalKcal = ((height + weight) * 10).toInt() // 예시 계산

            if (email.isNotEmpty() && password.isNotEmpty() && nickname.isNotEmpty()) {
                // Firebase Authentication으로 사용자 생성
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener { result ->
                        val uid = result.user?.uid ?: return@addOnSuccessListener

                        val userData = hashMapOf(
                            "email" to email,
                            "nickname" to nickname,
                            "height" to height,
                            "weight" to weight,
                            "goalKcal" to goalKcal
                        )

                        db.collection("users").document(uid).set(userData)
                            .addOnSuccessListener {
                                // SharedPreferences 저장
                                val prefs = getSharedPreferences("user", MODE_PRIVATE)
                                prefs.edit().putString("uid", uid).apply()

                                //  저장 후에 화면 이동
                                startActivity(Intent(this, Home::class.java))
                                finish()
                            }
                            .addOnFailureListener {
                                Toast.makeText(this, "Firestore 저장 실패", Toast.LENGTH_SHORT).show()
                            }
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show()
                    }

            } else {
                Toast.makeText(this, "모든 항목을 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
