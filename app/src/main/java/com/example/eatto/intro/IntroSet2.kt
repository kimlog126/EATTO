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

            if (email.isBlank() || password.isBlank() || nickname.isBlank()) {
                Toast.makeText(this, "모든 항목을 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (height <= 0f || weight <= 0f) {
                Toast.makeText(this, "키와 몸무게를 정확히 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val goalKcal = ((height + weight) * 10).toInt()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { result ->
                    val uid = result.user!!.uid

                    val userData = hashMapOf(
                        "email" to email,
                        "nickname" to nickname,
                        "height" to height,
                        "weight" to weight,
                        "goalKcal" to goalKcal
                    )

                    db.collection("users").document(uid).set(userData)
                        .addOnSuccessListener {
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
        }
    }

}
