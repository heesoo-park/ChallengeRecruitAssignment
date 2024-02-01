package com.example.challengerecruitassignment.todo

sealed interface TodoListItem {

    data class Item(
        val id: String?,
        val title: String?,
        val content: String?,
        val isBookmarked: Boolean? = false
    ) : TodoListItem
}