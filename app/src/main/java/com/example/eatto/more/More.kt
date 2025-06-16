package com.example.eatto.more

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.eatto.R
import com.example.eatto.community.Community
import com.example.eatto.home.Home
import com.example.eatto.report.Report
import com.google.android.material.bottomnavigation.BottomNavigationView

class More : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.selectedItemId = R.id.nav_more

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, Home::class.java))
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
                R.id.nav_more -> true
                else -> false
            }
        }
    }
}
