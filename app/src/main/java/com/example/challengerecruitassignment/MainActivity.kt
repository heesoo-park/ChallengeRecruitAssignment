package com.example.challengerecruitassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.challengerecruitassignment.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val adapter = ViewPagerAdapter(this)
        binding.viewPagerMain.adapter = adapter

        TabLayoutMediator(binding.tabLayoutMain, binding.viewPagerMain) { tab, pos ->
            when (pos) {
                0 -> tab.text = "Todo"
                1 -> tab.text = "Bookmark"
            }
        }.attach()
    }
}