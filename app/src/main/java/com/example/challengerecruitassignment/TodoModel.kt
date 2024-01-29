package com.example.challengerecruitassignment

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TodoModel(
    val id: String,
    val title: String,
    val description: String,
    val isBookmarked: Boolean = false
) : Parcelable
