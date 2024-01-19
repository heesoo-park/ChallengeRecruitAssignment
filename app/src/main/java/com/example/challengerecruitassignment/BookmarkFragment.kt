package com.example.challengerecruitassignment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challengerecruitassignment.databinding.FragmentBookmarkBinding
import com.example.challengerecruitassignment.databinding.FragmentTodoBinding

class BookmarkFragment : Fragment() {

    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding

    private val dummyData: ArrayList<Todo>
        get() = arrayListOf(
            Todo(
                "title 0",
                "description 0",
                false
            ),
            Todo(
                "title 1",
                "description 1",
                false
            ),
            Todo(
                "title 2",
                "description 2",
                false
            ),
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

        val adapter = BookmarkAdapter(dummyData)
        binding?.rvBookmark?.adapter = adapter
        binding?.rvBookmark?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
    }
}