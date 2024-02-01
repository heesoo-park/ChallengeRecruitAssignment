package com.example.challengerecruitassignment.todo

import com.example.challengerecruitassignment.TodoModel

sealed interface TodoListEvent {

    data class OpenContent(
        val position: Int,
        val item: TodoModel
    ): TodoListEvent
}