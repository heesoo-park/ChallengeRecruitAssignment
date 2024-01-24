package com.example.challengerecruitassignment.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.challengerecruitassignment.TodoModel
import com.example.challengerecruitassignment.manage.ManageTodoEntryType

class TodoListViewModel : ViewModel() {

    private val _uiState: MutableLiveData<TodoListUiState> = MutableLiveData(TodoListUiState.init())
    val uiState: LiveData<TodoListUiState> get() = _uiState

    fun onClick(todo: TodoModel?, entryType: ManageTodoEntryType, position: Int) {
        when (entryType) {
            ManageTodoEntryType.CREATE -> onClickRegister(todo)
            ManageTodoEntryType.UPDATE -> onClickUpdate(todo, position)
            ManageTodoEntryType.DELETE -> onClickDelete(todo)
        }
    }

    private fun onClickRegister(todo: TodoModel?) {
        if (todo == null) return

        _uiState.value = uiState.value?.copy(
            todoList = uiState.value?.todoList.orEmpty().toMutableList().apply {
                add(todo)
            }
        )
    }

    private fun onClickUpdate(todo: TodoModel?, position: Int) {
        if (todo == null) return

        _uiState.value = _uiState.value?.copy(
            todoList = uiState.value?.todoList.orEmpty().toMutableList().apply {
                set(position, todo)
            }
        )
    }

    private fun onClickDelete(todo: TodoModel?) {
        if (todo == null) return

        _uiState.value = _uiState.value?.copy(
            todoList = uiState.value?.todoList.orEmpty().toMutableList().apply {
                removeIf { it == todo }
            }
        )
    }
}