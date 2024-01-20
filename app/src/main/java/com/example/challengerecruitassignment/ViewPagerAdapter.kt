package com.example.challengerecruitassignment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.challengerecruitassignment.bookmark.BookmarkFragment
import com.example.challengerecruitassignment.todo.TodoFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TodoFragment()
            else -> BookmarkFragment()
        }
    }
}