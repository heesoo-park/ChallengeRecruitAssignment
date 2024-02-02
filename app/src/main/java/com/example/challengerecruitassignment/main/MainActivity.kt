package com.example.challengerecruitassignment.main

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

        initView()
    }

    private fun initView() = with(binding) {
        val adapter = ViewPagerAdapter(this@MainActivity)
        viewPagerMain.adapter = adapter
        viewPagerMain.offscreenPageLimit  = 1

        TabLayoutMediator(tabLayoutMain, viewPagerMain) { tab, position ->
            tab.setText(adapter.getTitle(position))
        }.attach()
    }
}