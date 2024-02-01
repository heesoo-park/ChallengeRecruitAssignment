package com.example.challengerecruitassignment.todo

enum class TodoListViewType {
    ITEM,
    UNKNOWN
    ;

    companion object {
        fun from(ordinal: Int): TodoListViewType = entries.find {
            it.ordinal == ordinal
        } ?: UNKNOWN
    }
}