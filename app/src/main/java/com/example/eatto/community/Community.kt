package com.example.eatto.community

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.eatto.R
import com.example.eatto.databinding.ActivityCommunityBinding
import com.example.eatto.home.Home
import com.example.eatto.more.More
import com.example.eatto.report.Report

class Community : AppCompatActivity() {

    private lateinit var binding: ActivityCommunityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommunityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 기본 탭 설정
        val defaultTab = intent.getStringExtra("defaultTab") ?: "feed"
        when (defaultTab) {
            "challenge" -> selectTab("challenge", ChallengeFragment())
            "magazine" -> selectTab("magazine", MagazineFragment())
            else -> selectTab("feed", FeedFragment())
        }

        // 하단 네비게이션
        binding.bottomNav.selectedItemId = R.id.nav_community
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, Home::class.java)); true
                }
                R.id.nav_report -> {
                    startActivity(Intent(this, Report::class.java)); true
                }
                R.id.nav_community -> true
                R.id.nav_more -> {
                    startActivity(Intent(this, More::class.java)); true
                }
                else -> false
            }
        }

        // 상단 탭 리스너
        binding.tabFeed.setOnClickListener {
            selectTab("feed", FeedFragment())
        }
        binding.tabChallenge.setOnClickListener {
            selectTab("challenge", ChallengeFragment())
        }
        binding.tabMagazine.setOnClickListener {
            selectTab("magazine", MagazineFragment())
        }

        // 글쓰기 버튼
        binding.writePostButton.setOnClickListener {
            startActivity(Intent(this, NewPostActivity::class.java))
        }
    }

    private fun selectTab(tag: String, fragment: Fragment) {
        showFragment(fragment)
        setTabSelected(tag)
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.communityContainer, fragment)
            .commit()
    }

    private fun setTabSelected(selected: String) {
        binding.tabFeed.setTextColor(getColor(if (selected == "feed") R.color.white else R.color.gray))
        binding.tabChallenge.setTextColor(getColor(if (selected == "challenge") R.color.white else R.color.gray))
        binding.tabMagazine.setTextColor(getColor(if (selected == "magazine") R.color.white else R.color.gray))
    }
}

