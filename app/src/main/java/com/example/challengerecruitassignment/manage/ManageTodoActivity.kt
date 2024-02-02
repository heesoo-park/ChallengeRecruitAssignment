package com.example.challengerecruitassignment.manage

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.example.challengerecruitassignment.main.TodoModel
import com.example.challengerecruitassignment.databinding.ActivityManageTodoBinding
import com.example.challengerecruitassignment.manage.ManageTodoConstant.EXTRA_TODO_ENTRY_TYPE
import com.example.challengerecruitassignment.manage.ManageTodoConstant.EXTRA_TODO_MODEL
import com.example.challengerecruitassignment.manage.ManageTodoConstant.EXTRA_TODO_POSITION

class ManageTodoActivity : AppCompatActivity() {

    companion object {

        fun newIntentForCreate(
            context: Context,
        ): Intent = Intent(
            context,
            ManageTodoActivity::class.java
        ).apply {
            putExtra(EXTRA_TODO_ENTRY_TYPE, ManageTodoEntryType.CREATE)
        }

        fun newIntentForUpdate(
            context: Context,
            todo: TodoModel,
            position: Int
        ): Intent = Intent(
            context,
            ManageTodoActivity::class.java
        ).apply {
            putExtra(EXTRA_TODO_ENTRY_TYPE, ManageTodoEntryType.UPDATE)
            putExtra(EXTRA_TODO_MODEL, todo)
            putExtra(EXTRA_TODO_POSITION, position)
        }
    }

    private val viewModel: ManageTodoViewModel by viewModels {
        ManageTodoSavedStateViewModelFactory(
            ManageTodoViewModelFactory(),
            this,
            intent.extras
        )
    }

    private val binding: ActivityManageTodoBinding by lazy {
        ActivityManageTodoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
        initViewModel()
    }

    private fun initView() = with(binding) {
        tbRegisterTodo.setNavigationOnClickListener {
            finish()
        }

        btnRegisterTodoRegister.setOnClickListener {
            viewModel.onClickRegister(
                etRegisterTodoTitle.text.toString(),
                etRegisterTodoDescription.text.toString()
            )
        }

        btnRegisterTodoUpdate.setOnClickListener {
            viewModel.onClickUpdate(
                etRegisterTodoTitle.text.toString(),
                etRegisterTodoDescription.text.toString()
            )
        }

        btnRegisterTodoDelete.setOnClickListener {
            viewModel.onClickDelete()
        }

        etRegisterTodoTitle.addTextChangedListener {
            btnRegisterTodoRegister.isEnabled = etRegisterTodoTitle.text.isNotEmpty()
        }
    }

    private fun initViewModel() = with(viewModel) {
        uiState.observe(this@ManageTodoActivity) {
            with(binding) {
                etRegisterTodoTitle.setText(it.title)
                etRegisterTodoDescription.setText(it.content)

                when (it.button) {
                    ManageTodoButtonUiState.Create -> {
                        btnRegisterTodoRegister.isVisible = true
                    }

                    ManageTodoButtonUiState.Update -> {
                        btnRegisterTodoDelete.isVisible = true
                        btnRegisterTodoUpdate.isVisible = true
                    }

                    else -> Unit
                }
            }
        }

        event.observe(this@ManageTodoActivity) {
            when (it) {
                is ManageTodoEvent.Update -> {
                    setResult(RESULT_OK, Intent().apply {
                        putExtra(
                            EXTRA_TODO_MODEL,
                            TodoModel(
                                id = it.id,
                                title = it.title,
                                description = it.content
                            )
                        )
                        putExtra(EXTRA_TODO_ENTRY_TYPE, ManageTodoEntryType.UPDATE)
                    })
                    finish()
                }

                is ManageTodoEvent.Register -> {
                    setResult(RESULT_OK, Intent().apply {
                        putExtra(
                            EXTRA_TODO_MODEL,
                            TodoModel(
                                id = it.id,
                                title = it.title,
                                description = it.content
                            )
                        )
                        putExtra(EXTRA_TODO_ENTRY_TYPE, ManageTodoEntryType.CREATE)
                    })
                    finish()
                }

                is ManageTodoEvent.Delete -> {
                    setResult(RESULT_OK, Intent().apply {
                        putExtra(
                            EXTRA_TODO_MODEL,
                            TodoModel(
                                id = it.id,
                                title = it.title,
                                description = it.content
                            )
                        )
                        putExtra(EXTRA_TODO_ENTRY_TYPE, ManageTodoEntryType.DELETE)
                    })
                    finish()
                }
            }
        }
    }
}