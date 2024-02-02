package com.example.challengerecruitassignment.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.challengerecruitassignment.main.TodoModel
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

    fun onCheckBookmark(
        item: TodoListItem
    ) {
        when (item) {
            is TodoListItem.Item -> {
                val temp = item.copy(
                    isBookmarked = item.isBookmarked?.not()
                )

                val mutableList = uiState.value?.todoList.orEmpty().toMutableList()
                val position = mutableList.indexOfFirst {
                    when (it) {
                        is TodoListItem.Item -> {
                            it.id == temp.id
                        }
                    }
                }

                _uiState.value = uiState.value?.copy(
                    todoList = mutableList.also {
                        it[position] = temp
                    }
                )

                _event.value = TodoListEvent.SendContent(temp)
            }
        }
    }

    // BookmarkList 프래그먼트에서 넘어온 값을 업데이트 시킬 때 사용하는 함수
    fun updateItem(
        entryType: ManageTodoEntryType?,
        item: TodoListItem?
    ) {
        if (item == null) {
            return
        }

        val mutableList = uiState.value?.todoList.orEmpty().toMutableList()

        when (item) {
            is TodoListItem.Item -> {
                val position =
                    mutableList.indexOfFirst {
                        when (it) {
                            is TodoListItem.Item -> {
                                it.id == item.id
                            }
                        }
                    }

                when (entryType) {
                    ManageTodoEntryType.UPDATE -> {
                        uiState.value?.copy(
                            todoList = mutableList.also { list ->
                                list[position] = item
                            }
                        )
                    }

                    ManageTodoEntryType.DELETE -> {
                        uiState.value?.copy(
                            todoList = mutableList.apply {
                                removeAt(position)
                            }
                        )
                    }

                    else -> null
                }
            }
        }?.let { state ->
            _uiState.value = state
        }
    }

    // ManageTodo 액티비티에서 넘어온 값을 업데이트 시킬 때 사용하는 함수
    fun updateItem(
        entryType: ManageTodoEntryType?,
        item: TodoModel?
    ) {
        if (item == null) {
            return
        }

        val mutableList = uiState.value?.todoList.orEmpty().toMutableList()

        val position = mutableList.indexOfFirst {
            when (it) {
                is TodoListItem.Item -> {
                    it.id == item.id
                }
            }
        }

        val target = mutableList.find {
            when (it) {
                is TodoListItem.Item -> {
                    it.id == item.id
                }
            }
        }

        when (entryType) {
            ManageTodoEntryType.UPDATE -> {
                val temp = when (target) {
                    is TodoListItem.Item -> {
                        target.copy(
                            title = item.title,
                            content = item.description
                        )
                    }

                    else -> null
                } as TodoListItem

                _event.value = TodoListEvent.SendContent(temp)

                uiState.value?.copy(
                    todoList = mutableList.also { list ->
                        list[position] = temp
                    }
                )
            }

            ManageTodoEntryType.CREATE -> {
                uiState.value?.copy(
                    todoList = mutableList.apply {
                        add(createTodoItem(item))
                    }
                )
            }

            ManageTodoEntryType.DELETE -> {
                val temp = when (target) {
                    is TodoListItem.Item -> {
                        target.copy(
                            title = item.title,
                            content = item.description,
                            isBookmarked = target.isBookmarked?.not()
                        )
                    }

                    else -> null
                } as TodoListItem

                _event.value = TodoListEvent.SendContent(temp)

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