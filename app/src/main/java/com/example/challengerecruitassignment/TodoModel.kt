package com.example.challengerecruitassignment

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TodoModel(
    var title: String,
    var description: String,
    var isBookmarked: Boolean = false
) : Parcelable
