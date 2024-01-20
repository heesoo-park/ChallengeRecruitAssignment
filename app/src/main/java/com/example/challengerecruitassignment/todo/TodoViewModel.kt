package com.example.challengerecruitassignment.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.challengerecruitassignment.DataStore
import com.example.challengerecruitassignment.Todo

class TodoViewModel : ViewModel() {

    private val _newTodo: MutableLiveData<Todo> = MutableLiveData()
    val newTodo: LiveData<Todo> get() = _newTodo

    fun onClickRegister(todo: Todo) {
        DataStore.addTodo(todo)
        _newTodo.value = todo
    }
}