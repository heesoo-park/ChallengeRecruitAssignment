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
import com.example.challengerecruitassignment.DataStore
import com.example.challengerecruitassignment.RegisterTodoActivity
import com.example.challengerecruitassignment.Todo
import com.example.challengerecruitassignment.databinding.FragmentTodoBinding

class TodoFragment : Fragment() {

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    private var _binding: FragmentTodoBinding? = null
    private val binding get() = _binding!!

    private val adapter = TodoAdapter(DataStore.getTotalTodoList())

    private val viewModel by lazy {
        ViewModelProvider(this@TodoFragment)[TodoViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    result?.data?.getParcelableExtra("todo", Todo::class.java)
                        ?.let { viewModel.onClickRegister(it) }
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

    private fun initViewModel() = with(viewModel) {
        newTodo.observe(viewLifecycleOwner) {
            adapter.notifyItemInserted(DataStore.getTotalTodoList().lastIndex)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()

        binding.rvTodo.adapter = adapter
        binding.rvTodo.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        binding.fabTodoAdd.setOnClickListener {
            val intent = Intent(requireContext(), RegisterTodoActivity::class.java)
            resultLauncher.launch(intent)
        }
    }
}