package com.example.challengerecruitassignment.manage

sealed interface ManageTodoEvent {

    data class Update(
        val id: String?,
        val title: String,
        val content: String
    ) : ManageTodoEvent

    data class Register(
        val id: String?,
        val title: String,
        val content: String
    ) : ManageTodoEvent

    data class Delete(
        val id: String?,
        val title: String?,
        val content: String?
    ) : ManageTodoEvent
}