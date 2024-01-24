package com.example.challengerecruitassignment.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.challengerecruitassignment.TodoModel
import com.example.challengerecruitassignment.databinding.BookmarkItemBinding

class BookmarkListAdapter: ListAdapter<TodoModel, BookmarkListAdapter.BookmarkViewHolder>(BookmarkComparator()) {

    inner class BookmarkViewHolder(private val binding: BookmarkItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: TodoModel) {
            binding.apply {
                tvBookmarkItemTitle.text = todo.title
                tvBookmarkItemDescription.text = todo.description
                switchBookmarkItemBookmark.isChecked = todo.isBookmarked
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        return BookmarkViewHolder(
            BookmarkItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class BookmarkComparator: DiffUtil.ItemCallback<TodoModel>() {
        override fun areItemsTheSame(oldItem: TodoModel, newItem: TodoModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: TodoModel, newItem: TodoModel): Boolean {
            return oldItem.title == newItem.title
        }
    }
}