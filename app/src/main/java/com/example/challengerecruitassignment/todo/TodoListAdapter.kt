package com.example.challengerecruitassignment.todo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.challengerecruitassignment.databinding.TodoItemBinding
import com.example.challengerecruitassignment.databinding.UnknownItemBinding

class TodoListAdapter(
    private val onClickItem: (Int, TodoListItem) -> Unit,
    private val onBookmarkChecked: (Int, TodoListItem) -> Unit
) : ListAdapter<TodoListItem, TodoListAdapter.TodoViewHolder>(
    object : DiffUtil.ItemCallback<TodoListItem>() {
    override fun areItemsTheSame(oldItem: TodoListItem, newItem: TodoListItem): Boolean =
        if (oldItem is TodoListItem.Item && newItem is TodoListItem.Item) {
            oldItem.id == newItem.id
        } else {
            oldItem == newItem
        }

    override fun areContentsTheSame(oldItem: TodoListItem, newItem: TodoListItem): Boolean =
        oldItem == newItem
}) {

    abstract class TodoViewHolder(view: View): RecyclerView.ViewHolder(view) {

        abstract fun onBind(item: TodoListItem)
    }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is TodoListItem.Item -> TodoListViewType.ITEM
        else -> TodoListViewType.UNKNOWN
    }.ordinal

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TodoViewHolder = when (TodoListViewType.from(viewType)) {
        TodoListViewType.ITEM -> TodoItemViewHolder(
            TodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onClickItem,
            onBookmarkChecked
        )

        else -> TodoUnknownViewHolder(
            UnknownItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        )
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class TodoItemViewHolder(
        private val binding: TodoItemBinding,
        private val onClickItem: (Int, TodoListItem) -> Unit,
        private val onBookmarkChecked: (Int, TodoListItem) -> Unit
    ) : TodoViewHolder(binding.root) {

        override fun onBind(item: TodoListItem) {
            if (item is TodoListItem.Item) {
                binding.tvTodoItemTitle.text = item.title
                binding.tvTodoItemDescription.text = item.content
                binding.constraintLayoutTodoItem.setOnClickListener {
                    onClickItem.invoke(
                        adapterPosition,
                        item,
                    )
                }
            }
        }
    }

    class TodoUnknownViewHolder(
        private val binding: UnknownItemBinding,
    ) : TodoViewHolder(binding.root) {

        override fun onBind(item: TodoListItem) = Unit
    }
}