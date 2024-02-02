package com.example.challengerecruitassignment.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.challengerecruitassignment.bookmark.BookmarkListItem
import com.example.challengerecruitassignment.manage.ManageTodoEntryType
import com.example.challengerecruitassignment.todo.TodoListItem

class SharedViewModel : ViewModel() {

    private val _sharedEvent: MutableLiveData<SharedEvent> = MutableLiveData()
    val sharedEvent: LiveData<SharedEvent> get() = _sharedEvent

    fun sendTodoItem(item: TodoListItem) {
        _sharedEvent.value = SharedEvent.SendToBookmark(changeToBookmarkItem(item))
    }

    fun sendBookmarkItem(entryType: ManageTodoEntryType, item: BookmarkListItem) {
        _sharedEvent.value = SharedEvent.SendToTodo(entryType, changeToTodoItem(item))
    }

    private fun changeToBookmarkItem(item: TodoListItem): BookmarkListItem =
        when (item) {
            is TodoListItem.Item -> {
                BookmarkListItem.Item(
                    id = item.id,
                    title = item.title,
                    content = item.content,
                    isBookmarked = item.isBookmarked
                )
            }
        }

    private fun changeToTodoItem(item: BookmarkListItem): TodoListItem =
        when (item) {
            is BookmarkListItem.Item -> {
                TodoListItem.Item(
                    id = item.id,
                    title = item.title,
                    content = item.content,
                    isBookmarked = item.isBookmarked
                )
            }
        }
}