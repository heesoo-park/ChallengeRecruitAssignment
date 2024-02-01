package com.example.challengerecruitassignment.manage

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.example.challengerecruitassignment.TodoModel
import com.example.challengerecruitassignment.manage.ManageTodoConstant.EXTRA_TODO_ENTRY_TYPE
import com.example.challengerecruitassignment.manage.ManageTodoConstant.EXTRA_TODO_MODEL
import java.util.UUID

class ManageTodoViewModel(
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val entryType get() = savedStateHandle.get<ManageTodoEntryType>(EXTRA_TODO_ENTRY_TYPE)
    private val entity get() = savedStateHandle.get<TodoModel>(EXTRA_TODO_MODEL)

    private val _event: MutableLiveData<ManageTodoEvent> = MutableLiveData()
    val event: LiveData<ManageTodoEvent> get() = _event

    private val _uiState: MutableLiveData<ManageTodoUiState> = MutableLiveData(ManageTodoUiState.init())
    val uiState: LiveData<ManageTodoUiState> get() = _uiState

    init {
        _uiState.value = uiState.value?.copy(
            title = entity?.title,
            content = entity?.description,
            button = if (entryType == ManageTodoEntryType.UPDATE) {
                ManageTodoButtonUiState.Update
            } else {
                ManageTodoButtonUiState.Create
            }
        )
    }

    fun onClickUpdate(
        title: String,
        description: String
    ) {
        _event.value = ManageTodoEvent.Update(
            id = entity?.id,
            title = title,
            content = description
        )
    }

    fun onClickRegister(
        title: String,
        description: String
    ) {
        _event.value = ManageTodoEvent.Register(
            id = UUID.randomUUID().toString(),
            title = title,
            content = description
        )
    }

    fun onClickDelete() {
        _event.value = ManageTodoEvent.Delete(
            id = entity?.id,
            title = entity?.title,
            content = entity?.description
        )
    }
}

class ManageTodoViewModelFactory() {

    fun create(
        savedStateHandle: SavedStateHandle
    ) = ManageTodoViewModel(
        savedStateHandle
    )
}

class ManageTodoSavedStateViewModelFatory(
    private val factory: ManageTodoViewModelFactory,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T = factory.create(handle) as T

}