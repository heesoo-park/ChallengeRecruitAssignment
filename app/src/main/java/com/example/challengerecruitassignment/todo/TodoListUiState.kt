package com.example.challengerecruitassignment.todo

data class TodoListUiState(
    val todoList: List<TodoListItem>
) {
    companion object {
        fun init() = TodoListUiState(
            todoList = emptyList()
        )
    }
}
