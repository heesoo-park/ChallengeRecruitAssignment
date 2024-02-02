package com.example.challengerecruitassignment.todo

import com.example.challengerecruitassignment.main.TodoModel

sealed interface TodoListEvent {

    data class OpenContent(
        val position: Int,
        val item: TodoModel
    ): TodoListEvent

    data class SendContent(
        val item: TodoListItem
    ) : TodoListEvent
}