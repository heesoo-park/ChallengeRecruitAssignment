package com.example.challengerecruitassignment.todo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.challengerecruitassignment.TodoModel
import com.example.challengerecruitassignment.databinding.TodoItemBinding

class TodoListAdapter : ListAdapter<TodoModel, TodoListAdapter.TodoViewHolder>(TodoComparator()) {

    interface TodoClick {
        fun onClick(todo: TodoModel, position: Int)
    }

    var todoClick: TodoClick? = null

    inner class TodoViewHolder(private val binding: TodoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(todo: TodoModel) {
            binding.apply {
                tvTodoItemTitle.text = todo.title
                tvTodoItemDescription.text = todo.description
                switchTodoItemBookmark.isChecked = todo.isBookmarked
            }
        }

        fun connectInterface(position: Int) {
            binding.constraintLayoutTodoItem.setOnClickListener {
                todoClick?.onClick(currentList[position], position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            TodoItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(currentList[position])
        holder.connectInterface(position)
    }

    class TodoComparator : DiffUtil.ItemCallback<TodoModel>() {
        override fun areItemsTheSame(oldItem: TodoModel, newItem: TodoModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: TodoModel, newItem: TodoModel): Boolean {
            return oldItem.id == newItem.id
        }
    }
}