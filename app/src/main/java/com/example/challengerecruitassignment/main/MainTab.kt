package com.example.challengerecruitassignment.main

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

data class MainTab(
    val fragment: Fragment,
    @StringRes val title: Int
)
