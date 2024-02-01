package com.example.challengerecruitassignment.manage

data class ManageTodoUiState(
    val title: String?,
    val content: String?,
    val button: ManageTodoButtonUiState?
) {
    companion object {
        fun init() = ManageTodoUiState(
            title = null,
            content = null,
            button = null
        )
    }
}

sealed interface ManageTodoButtonUiState {
    data object Create: ManageTodoButtonUiState
    data object Update: ManageTodoButtonUiState
}