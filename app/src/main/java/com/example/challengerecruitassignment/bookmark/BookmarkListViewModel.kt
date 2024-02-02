package com.example.challengerecruitassignment.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.challengerecruitassignment.main.TodoModel
import com.example.challengerecruitassignment.manage.ManageTodoEntryType

class BookmarkListViewModel : ViewModel() {

    private val _event: MutableLiveData<BookmarkListEvent> = MutableLiveData()
    val event: LiveData<BookmarkListEvent> get() = _event

    private val _uiState: MutableLiveData<BookmarkListUiState> =
        MutableLiveData(BookmarkListUiState.init())
    val uiState: LiveData<BookmarkListUiState> get() = _uiState

    fun onClickItem(
        position: Int,
        item: BookmarkListItem
    ) {
        _event.value = BookmarkListEvent.OpenContent(
            position,
            when (item) {
                is BookmarkListItem.Item -> TodoModel(
                    id = item.id,
                    title = item.title,
                    description = item.content
                )
            }
        )
    }

    fun onCheckBookmark(
        item: BookmarkListItem
    ) {
        when (item) {
            is BookmarkListItem.Item -> {
                val temp = item.copy(
                    isBookmarked = item.isBookmarked?.not()
                )

                val mutableList =
                    uiState.value?.bookmarkList.orEmpty().toMutableList()
                val position = mutableList.indexOfFirst {
                    when (it) {
                        is BookmarkListItem.Item -> {
                            it.id == temp.id
                        }
                    }
                }

                _uiState.value = uiState.value?.copy(
                    bookmarkList = mutableList.apply {
                        removeAt(position)
                    }
                )

                _event.value = BookmarkListEvent.SendContent(ManageTodoEntryType.UPDATE, temp)
            }
        }
    }

    // TodoList 프래그먼트에서 넘어온 값을 업데이트 시킬 때 사용하는 함수
    fun updateItem(
        item: BookmarkListItem
    ) {
        val mutableList = uiState.value?.bookmarkList.orEmpty().toMutableList()

        when (item) {
            is BookmarkListItem.Item -> {
                val position =
                    mutableList.indexOfFirst {
                        when (it) {
                            is BookmarkListItem.Item -> {
                                it.id == item.id
                            }
                        }
                    }

                if (position != -1) {
                    if (item.isBookmarked == true) {
                        uiState.value?.copy(
                            bookmarkList = mutableList.also { list ->
                                list[position] = item
                            }
                        )
                    } else {
                        uiState.value?.copy(
                            bookmarkList = mutableList.apply {
                                removeAt(position)
                            }
                        )
                    }
                } else {
                    uiState.value?.copy(
                        bookmarkList = mutableList.apply {
                            add(item)
                        }
                    )
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

        val mutableList = uiState.value?.bookmarkList.orEmpty().toMutableList()

        val position = mutableList.indexOfFirst {
            when (it) {
                is BookmarkListItem.Item -> {
                    it.id == item.id
                }
            }
        }

        val target =  mutableList.find {
            when (it) {
                is BookmarkListItem.Item -> {
                    it.id == item.id
                }
            }
        }

        when (entryType) {
            ManageTodoEntryType.UPDATE -> {
                val temp = when (target) {
                    is BookmarkListItem.Item -> {
                        target.copy(
                            title = item.title,
                            content = item.description
                        )
                    }
                    else -> null
                } as BookmarkListItem

                _event.value = BookmarkListEvent.SendContent(entryType, temp)

                uiState.value?.copy(
                    bookmarkList  = mutableList.also { list ->
                        list[position] = temp
                    }
                )
            }

            ManageTodoEntryType.DELETE -> {
                position.takeIf { it != -1 }?.let {
                    val temp = when (target) {
                        is BookmarkListItem.Item -> {
                            target.copy(
                                title = item.title,
                                content = item.description,
                                isBookmarked = target.isBookmarked?.not()
                            )
                        }
                        else -> null
                    } as BookmarkListItem

                    _event.value = BookmarkListEvent.SendContent(entryType, temp)

                    uiState.value?.copy(
                        bookmarkList  = mutableList.apply {
                            removeAt(it)
                        }
                    )
                }
            }

            else -> null
        }?.let { state ->
            _uiState.value = state
        }
    }
}