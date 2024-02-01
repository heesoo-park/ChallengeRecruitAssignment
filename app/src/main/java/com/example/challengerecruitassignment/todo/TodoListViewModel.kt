package com.example.challengerecruitassignment.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.challengerecruitassignment.TodoModel
import com.example.challengerecruitassignment.manage.ManageTodoEntryType

class TodoListViewModel : ViewModel() {

    private val _event: MutableLiveData<TodoListEvent> = MutableLiveData()
    val event: LiveData<TodoListEvent> get() = _event

    private val _uiState: MutableLiveData<TodoListUiState> = MutableLiveData(TodoListUiState.init())
    val uiState: LiveData<TodoListUiState> get() = _uiState

    fun onClickItem(
        position: Int,
        item: TodoListItem
    ) {
        _event.value = TodoListEvent.OpenContent(
            position,
            when (item) {
                is TodoListItem.Item -> TodoModel(
                    id = item.id,
                    title = item.title,
                    description = item.content
                )
            }
        )
    }

    fun updateTodoItem(
        entryType: ManageTodoEntryType?,
        todo: TodoModel?
    ) {
        if (todo == null) {
            return
        }

        val mutableList = uiState.value?.todoList.orEmpty().toMutableList()

        when (entryType) {
            ManageTodoEntryType.UPDATE -> {
                val position = mutableList.indexOfFirst {
                    when (it) {
                        is TodoListItem.Item -> {
                            it.id == todo.id
                        }
                    }
                }

                uiState.value?.copy(
                    todoList = mutableList.also { list ->
                        list[position] = createTodoItem(todo)
                    }
                )
            }

            ManageTodoEntryType.CREATE -> {
                uiState.value?.copy(
                    todoList = mutableList.apply {
                        add(createTodoItem(todo))
                    }
                )
            }

            ManageTodoEntryType.DELETE -> {
                val position = mutableList.indexOfFirst {
                    when (it) {
                        is TodoListItem.Item -> {
                            it.id == todo.id
                        }
                    }
                }

                uiState.value?.copy(
                    todoList = mutableList.apply {
                        removeAt(position)
                    }
                )
            }

            else -> null
        }?.let { state ->
            _uiState.value = state
        }
    }

    private fun createTodoItem(model: TodoModel): TodoListItem = TodoListItem.Item(
        id = model.id,
        title = model.title,
        content = model.description
    )
}