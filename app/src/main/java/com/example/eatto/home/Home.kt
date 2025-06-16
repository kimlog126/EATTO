package com.example.eatto.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.eatto.R
import com.example.eatto.community.Community
import com.example.eatto.databinding.ActivityHomeBinding
import com.example.eatto.more.More
import com.example.eatto.report.Report
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class Home : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val auth = FirebaseAuth.getInstance()
    private val client = OkHttpClient()
    private val apiKey = "50875245-0e137f986f3f32d95a0321e67"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val uid = auth.currentUser?.uid
        if (uid != null) {
            FirebaseFirestore.getInstance().collection("users").document(uid)
                .get()
                .addOnSuccessListener { document ->
                    val goalKcal = document.getLong("goalKcal")?.toInt() ?: 0
                    val nickname = document.getString("nickname") ?: "회원"

                    binding.goalKcal.text = "${goalKcal}\n권장"
                    binding.hello.text = "안녕하세요, ${nickname}님!"
                }
        }

        val food1 = binding.food1.text.toString()
        val food2 = binding.food2.text.toString()
        val food3 = binding.food3.text.toString()

        fetchFoodImage(food1, binding.morningImage)
        fetchFoodImage(food2, binding.lunchImage)
        fetchFoodImage(food3, binding.dinnerImage)

        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true
                R.id.nav_report -> {
                    startActivity(Intent(this, Report::class.java))
                    finish()
                    true
                }
                R.id.nav_community -> {
                    startActivity(Intent(this, Community::class.java))
                    finish()
                    true
                }
                R.id.nav_more -> {
                    startActivity(Intent(this, More::class.java))
                    finish()
                    true
                }
                else -> false
            }
        }

        binding.imageButton.setOnClickListener {
            val intent = Intent(this, Community::class.java)
            intent.putExtra("defaultTab", "challenge")
            startActivity(intent)
        }
    }

    private fun fetchFoodImage(foodName: String, imageView: ImageView) {
        lifecycleScope.launch {
            try {
                val imageUrl = withContext(Dispatchers.IO) {
                    val url = "https://pixabay.com/api/?key=$apiKey&q=${foodName}&image_type=photo&orientation=horizontal&per_page=3&lang=ko&category=food&editors_choice=true&order=popular&safesearch=true"
                    val request = Request.Builder().url(url).build()
                    val response = client.newCall(request).execute()
                    val body = response.body?.string()

                    if (response.isSuccessful && !body.isNullOrEmpty()) {
                        val json = JSONObject(body)
                        val hits = json.getJSONArray("hits")
                        if (hits.length() > 0) {
                            hits.getJSONObject(0).getString("webformatURL")
                        } else null
                    } else null
                }

                imageUrl?.let {
                    Glide.with(imageView.context)
                        .load(it)
                        .centerCrop()
                        .into(imageView)
                }
            } catch (e: Exception) {
                Log.e("Pixabay", "이미지 요청 중 오류", e)
            }
        }
    }

}
