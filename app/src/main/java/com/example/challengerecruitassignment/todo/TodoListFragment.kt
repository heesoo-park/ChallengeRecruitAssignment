package com.example.challengerecruitassignment.todo

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.IntentCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challengerecruitassignment.manage.ManageTodoActivity
import com.example.challengerecruitassignment.main.TodoModel
import com.example.challengerecruitassignment.databinding.FragmentTodoBinding
import com.example.challengerecruitassignment.main.SharedEvent
import com.example.challengerecruitassignment.main.SharedViewModel
import com.example.challengerecruitassignment.manage.ManageTodoConstant.EXTRA_TODO_ENTRY_TYPE
import com.example.challengerecruitassignment.manage.ManageTodoConstant.EXTRA_TODO_MODEL
import com.example.challengerecruitassignment.manage.ManageTodoEntryType

class TodoListFragment : Fragment() {

    companion object {
        fun newInstance() = TodoListFragment()
    }

    private val updateTodoLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val todo = IntentCompat.getParcelableExtra(
                    result.data ?: Intent(),
                    EXTRA_TODO_MODEL,
                    TodoModel::class.java
                )
                val entryType = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    result.data?.getSerializableExtra(
                        EXTRA_TODO_ENTRY_TYPE,
                        ManageTodoEntryType::class.java
                    )
                } else {
                    result.data?.getSerializableExtra(EXTRA_TODO_ENTRY_TYPE) as ManageTodoEntryType
                }

                viewModel.updateItem(entryType, todo)
            }
        }

    private var _binding: FragmentTodoBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val viewModel: TodoListViewModel by viewModels()

    private val adapter: TodoListAdapter by lazy {
        TodoListAdapter(
            onClickItem = { position, item ->
                viewModel.onClickItem(position, item)
            },
            onBookmarkChecked = { item ->
                viewModel.onCheckBookmark(item)
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initViewModel()
        initSharedViewModel()
    }

    private fun initView() = with(binding) {
        rvTodo.adapter = adapter
        rvTodo.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        fabTodoAdd.setOnClickListener {
            updateTodoLauncher.launch(
                ManageTodoActivity.newIntentForCreate(requireContext())
            )
        }
    }

    private fun initViewModel() = with(viewModel) {
        uiState.observe(viewLifecycleOwner) {
            adapter.submitList(it.todoList.toList())
        }

        event.observe(viewLifecycleOwner) {
            when (it) {
                is TodoListEvent.OpenContent -> {
                    updateTodoLauncher.launch(
                        ManageTodoActivity.newIntentForUpdate(
                            requireContext(),
                            it.item,
                            it.position
                        )
                    )
                }

                is TodoListEvent.SendContent -> {
                    sharedViewModel.sendTodoItem(it.item)
                }
            }
        }
    }

    private fun initSharedViewModel() = with(sharedViewModel) {
        sharedEvent.observe(viewLifecycleOwner) {
            when (it) {
                is SharedEvent.SendToTodo -> {
                    viewModel.updateItem(it.entryType, it.item)
                }

                else -> Unit
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}