package com.example.challengerecruitassignment.main

import com.example.challengerecruitassignment.bookmark.BookmarkListItem
import com.example.challengerecruitassignment.manage.ManageTodoEntryType
import com.example.challengerecruitassignment.todo.TodoListItem

sealed interface SharedEvent {

    data class SendToBookmark(
        val item: BookmarkListItem
    ): SharedEvent

    data class SendToTodo(
        val entryType: ManageTodoEntryType,
        val item: TodoListItem
    ): SharedEvent
}