package com.example.challengerecruitassignment.manage

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import com.example.challengerecruitassignment.TodoModel
import com.example.challengerecruitassignment.databinding.ActivityManageTodoBinding

class ManageTodoActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ENTRY_TYPE = "extra_entry_type"
        const val EXTRA_TODO_MODEL = "extra_todo_model"
        const val EXTRA_TODO_POSITION = "extra_todo_position"

        fun newIntentForCreate(
            context: Context,
        ): Intent = Intent(
            context,
            ManageTodoActivity::class.java
        ).apply {
            putExtra(EXTRA_ENTRY_TYPE, ManageTodoEntryType.CREATE)
        }

        fun newIntentForUpdate(
            context: Context,
            todo: TodoModel,
            position: Int
        ): Intent = Intent(
            context,
            ManageTodoActivity::class.java
        ).apply {
            putExtra(EXTRA_ENTRY_TYPE, ManageTodoEntryType.UPDATE.ordinal)
            putExtra(EXTRA_TODO_MODEL, todo)
            putExtra(EXTRA_TODO_POSITION, position)
        }
    }

    private val binding: ActivityManageTodoBinding by lazy {
        ActivityManageTodoBinding.inflate(layoutInflater)
    }

    private val entryType: ManageTodoEntryType by lazy {
        ManageTodoEntryType.getEntryType(
            intent?.getIntExtra(
                EXTRA_ENTRY_TYPE,
                ManageTodoEntryType.CREATE.ordinal
            )
        )
    }

    private val todo: TodoModel? by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_TODO_MODEL, TodoModel::class.java)
        } else {
            intent.getParcelableExtra(EXTRA_TODO_MODEL)
        }
    }

    private val position: Int? by lazy {
        intent.getIntExtra(EXTRA_TODO_POSITION, 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
    }

    private fun initView() = with(binding) {
        etRegisterTodoTitle.setText(todo?.title)
        etRegisterTodoDescription.setText(todo?.description)

        if (entryType == ManageTodoEntryType.UPDATE) {
            btnRegisterTodoRegister.visibility = View.GONE
            btnRegisterTodoDelete.visibility = View.VISIBLE
            btnRegisterTodoUpdate.visibility = View.VISIBLE
        }

        tbRegisterTodo.setNavigationOnClickListener {
            finish()
        }

        btnRegisterTodoRegister.setOnClickListener {
            val newIntent = Intent()
            newIntent.putExtra(
                EXTRA_TODO_MODEL,
                TodoModel(
                    etRegisterTodoTitle.text.toString(),
                    etRegisterTodoDescription.text.toString()
                )
            )
            newIntent.putExtra(EXTRA_ENTRY_TYPE, entryType.ordinal)
            setResult(RESULT_OK, newIntent)
            finish()
        }

        btnRegisterTodoUpdate.setOnClickListener {
            val newIntent = Intent()
            newIntent.putExtra(
                EXTRA_TODO_MODEL,
                TodoModel(
                    etRegisterTodoTitle.text.toString(),
                    etRegisterTodoDescription.text.toString()
                )
            )
            newIntent.putExtra(EXTRA_ENTRY_TYPE, entryType.ordinal)
            newIntent.putExtra(EXTRA_TODO_POSITION, position)
            setResult(RESULT_OK, newIntent)
            finish()
        }

        btnRegisterTodoDelete.setOnClickListener {
            val newIntent = Intent()
            newIntent.putExtra(
                EXTRA_TODO_MODEL,
                TodoModel(
                    todo?.title.toString(),
                    todo?.description.toString()
                )
            )
            newIntent.putExtra(EXTRA_ENTRY_TYPE, ManageTodoEntryType.DELETE.ordinal)
            setResult(RESULT_OK, newIntent)
            finish()
        }

        etRegisterTodoTitle.addTextChangedListener {
            btnRegisterTodoRegister.isEnabled = etRegisterTodoTitle.text.isNotEmpty()
        }
    }
}