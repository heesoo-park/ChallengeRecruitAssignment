package com.example.challengerecruitassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2
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

        binding.viewPagerMain.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> {
                        binding.fabMainAdd.isVisible = true
                    }
                    else -> {
                        binding.fabMainAdd.isVisible = false
                    }
                }
            }
        })

        binding.fabMainAdd.setOnClickListener {
            val intent = Intent(this@MainActivity, RegisterTodoActivity::class.java)
            startActivity(intent)
        }
    }
}