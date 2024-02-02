package com.example.challengerecruitassignment.main

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TodoModel(
    val id: String?,
    val title: String?,
    val description: String?,
) : Parcelable