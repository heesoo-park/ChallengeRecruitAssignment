package com.example.challengerecruitassignment.todo

import com.example.challengerecruitassignment.TodoModel

data class TodoListUiState(
    val todoList: List<TodoModel>
) {
    companion object {
        fun init() = TodoListUiState(
            todoList = emptyList()
        )
    }
}
