package com.example.challengerecruitassignment.todo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.challengerecruitassignment.Todo
import com.example.challengerecruitassignment.databinding.TodoItemBinding

class TodoAdapter(val dataList: ArrayList<Todo>):
    RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    inner class TodoViewHolder(private val binding: TodoItemBinding): RecyclerView.ViewHolder(binding.root) {
        val title = binding.tvTodoItemTitle
        val description = binding.tvTodoItemDescription
        var isBookmarked = binding.switchTodoItemBookmark
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(TodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.title.text = dataList[position].title
        holder.description.text = dataList[position].description
        holder.isBookmarked.isChecked = dataList[position].isBookmarked
    }
}