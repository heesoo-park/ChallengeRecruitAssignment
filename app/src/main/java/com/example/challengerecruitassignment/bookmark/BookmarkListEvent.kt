package com.example.challengerecruitassignment.bookmark

import com.example.challengerecruitassignment.main.TodoModel
import com.example.challengerecruitassignment.manage.ManageTodoEntryType

sealed interface BookmarkListEvent {

    data class OpenContent(
        val position: Int,
        val item: TodoModel
    ): BookmarkListEvent

    data class SendContent(
        val entryType: ManageTodoEntryType,
        val item: BookmarkListItem
    ) : BookmarkListEvent
}