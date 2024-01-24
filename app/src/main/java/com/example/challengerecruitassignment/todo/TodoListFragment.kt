package com.example.challengerecruitassignment.todo

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challengerecruitassignment.manage.ManageTodoActivity
import com.example.challengerecruitassignment.TodoModel
import com.example.challengerecruitassignment.databinding.FragmentTodoBinding
import com.example.challengerecruitassignment.manage.ManageTodoEntryType

class TodoListFragment : Fragment() {

    companion object {
        fun newInstance() = TodoListFragment()
    }

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    private var _binding: FragmentTodoBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy {
        ViewModelProvider(this@TodoListFragment)[TodoListViewModel::class.java]
    }

    private val adapter = TodoListAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val todo = result?.data?.getParcelableExtra(
                        ManageTodoActivity.EXTRA_TODO_MODEL,
                        TodoModel::class.java
                    )
                    val entryType = ManageTodoEntryType.getEntryType(
                        result?.data?.getIntExtra(
                            ManageTodoActivity.EXTRA_ENTRY_TYPE,
                            ManageTodoEntryType.CREATE.ordinal
                        )
                    )
                    val position = result?.data?.getIntExtra("position", 0)
                    viewModel.onClick(todo, entryType, position ?: 0)
                }
            }
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
    }

    private fun initView() = with(binding) {
        rvTodo.adapter = adapter
        rvTodo.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        adapter.todoClick = object : TodoListAdapter.TodoClick {
            override fun onClick(todo: TodoModel, position: Int) {
                resultLauncher.launch(
                    ManageTodoActivity.newIntentForUpdate(requireContext(), todo, position)
                )
            }
        }

        fabTodoAdd.setOnClickListener {
            resultLauncher.launch(
                ManageTodoActivity.newIntentForCreate(requireContext())
            )
        }
    }

    private fun initViewModel() = with(viewModel) {
        uiState.observe(viewLifecycleOwner) {
            adapter.submitList(it.todoList.toList())
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}