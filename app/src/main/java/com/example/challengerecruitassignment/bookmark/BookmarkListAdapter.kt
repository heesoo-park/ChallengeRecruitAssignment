package com.example.challengerecruitassignment.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.challengerecruitassignment.databinding.BookmarkItemBinding

class BookmarkListAdapter(
    private val onClickItem: (Int, BookmarkListItem) -> Unit,
    private val onBookmarkChecked: (BookmarkListItem) -> Unit
) : ListAdapter<BookmarkListItem, BookmarkListAdapter.BookmarkViewHolder>(
    object : DiffUtil.ItemCallback<BookmarkListItem>() {
        override fun areItemsTheSame(
            oldItem: BookmarkListItem,
            newItem: BookmarkListItem
        ): Boolean =
            if (oldItem is BookmarkListItem.Item && newItem is BookmarkListItem.Item) {
                oldItem.id == newItem.id
            } else {
                oldItem == newItem
            }

        override fun areContentsTheSame(
            oldItem: BookmarkListItem,
            newItem: BookmarkListItem
        ): Boolean =
            oldItem == newItem
    }
) {

    class BookmarkViewHolder(
        private val binding: BookmarkItemBinding,
        private val onClickItem: (Int, BookmarkListItem) -> Unit,
        private val onBookmarkChecked: (BookmarkListItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: BookmarkListItem) {
            if (todo is BookmarkListItem.Item) {
                binding.apply {
                    tvBookmarkItemTitle.text = todo.title
                    tvBookmarkItemDescription.text = todo.content
                    switchBookmarkItemBookmark.isChecked = todo.isBookmarked ?: false
                    constraintLayoutBookmarkItem.setOnClickListener {
                        onClickItem.invoke(
                            adapterPosition,
                            todo
                        )
                    }
                    switchBookmarkItemBookmark.setOnClickListener {
                        onBookmarkChecked.invoke(
                            todo
                        )
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        return BookmarkViewHolder(
            BookmarkItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onClickItem,
            onBookmarkChecked
        )
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}