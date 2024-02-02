package com.example.challengerecruitassignment.bookmark

sealed interface BookmarkListItem {

    data class Item(
        val id: String?,
        val title: String?,
        val content: String?,
        val isBookmarked: Boolean? = false
    ) : BookmarkListItem
}