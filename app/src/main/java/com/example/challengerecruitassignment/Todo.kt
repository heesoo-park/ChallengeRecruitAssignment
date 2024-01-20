package com.example.challengerecruitassignment

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Todo(
    val title: String,
    val description: String,
    var isBookmarked: Boolean = false
) : Parcelable
