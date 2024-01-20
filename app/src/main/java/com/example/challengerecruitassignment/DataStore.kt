package com.example.challengerecruitassignment

object DataStore {
    private val totalTodoList: ArrayList<Todo> = ArrayList()

    init {
        totalTodoList.add(
            Todo(
                "title 0",
                "description 0"
            )
        )
        totalTodoList.add(
            Todo(
                "title 1",
                "description 1"
            )
        )
        totalTodoList.add(
            Todo(
                "title 2",
                "description 2"
            )
        )
    }

    fun getTotalTodoList(): ArrayList<Todo> {
        return totalTodoList
    }

    fun addTodo(todo: Todo) {
        totalTodoList.add(todo)
    }
}