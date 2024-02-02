package com.example.challengerecruitassignment.bookmark

import android.app.Activity
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
import com.example.challengerecruitassignment.main.TodoModel
import com.example.challengerecruitassignment.databinding.FragmentBookmarkBinding
import com.example.challengerecruitassignment.main.SharedEvent
import com.example.challengerecruitassignment.manage.ManageTodoActivity
import com.example.challengerecruitassignment.manage.ManageTodoConstant
import com.example.challengerecruitassignment.manage.ManageTodoEntryType
import com.example.challengerecruitassignment.main.SharedViewModel

class BookmarkListFragment : Fragment() {

    companion object {
        fun newInstance() = BookmarkListFragment()
    }

    private val updateTodoLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val todo = IntentCompat.getParcelableExtra(
                    result.data ?: Intent(),
                    ManageTodoConstant.EXTRA_TODO_MODEL,
                    TodoModel::class.java
                )
                val entryType = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    result.data?.getSerializableExtra(
                        ManageTodoConstant.EXTRA_TODO_ENTRY_TYPE,
                        ManageTodoEntryType::class.java
                    )
                } else {
                    result.data?.getSerializableExtra(ManageTodoConstant.EXTRA_TODO_ENTRY_TYPE) as ManageTodoEntryType
                }

                viewModel.updateItem(entryType, todo)
            }
        }

    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val viewModel: BookmarkListViewModel by viewModels()

    private val adapter = BookmarkListAdapter(
        onClickItem = { position, item ->
            viewModel.onClickItem(position, item)
        },
        onBookmarkChecked = { item ->
            viewModel.onCheckBookmark(item)
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initSharedViewModel()

        binding?.rvBookmark?.adapter = adapter
        binding?.rvBookmark?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    private fun initSharedViewModel() = with(sharedViewModel) {
        sharedEvent.observe(viewLifecycleOwner) {
            when (it) {
                is SharedEvent.SendToBookmark -> {
                    viewModel.updateItem(it.item)
                }

                else -> Unit
            }
        }
    }

    private fun initViewModel() = with(viewModel) {
        uiState.observe(viewLifecycleOwner) {
            adapter.submitList(it.bookmarkList.toList())
        }

        event.observe(viewLifecycleOwner) {
            when (it) {
                is BookmarkListEvent.OpenContent -> {
                    updateTodoLauncher.launch(
                        ManageTodoActivity.newIntentForUpdate(
                            requireContext(),
                            it.item,
                            it.position
                        )
                    )
                }

                is BookmarkListEvent.SendContent -> {
                    sharedViewModel.sendBookmarkItem(it.entryType, it.item)
                }
            }
        }
    }
}