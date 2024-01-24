package com.example.challengerecruitassignment.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.challengerecruitassignment.R
import com.example.challengerecruitassignment.bookmark.BookmarkListFragment
import com.example.challengerecruitassignment.todo.TodoListFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

    private val fragmentList = listOf(
        MainTab(TodoListFragment.newInstance(), R.string.main_tab_todo),
        MainTab(BookmarkListFragment.newInstance(), R.string.main_tab_bookmark)
    )

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment = fragmentList[position].fragment

    fun getTitle(position: Int): Int = fragmentList[position].title
}