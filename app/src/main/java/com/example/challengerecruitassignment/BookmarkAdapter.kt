package com.example.challengerecruitassignment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.challengerecruitassignment.databinding.BookmarkItemBinding

class BookmarkAdapter(private val dataList: ArrayList<Todo>) :
    RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder>() {

    inner class BookmarkViewHolder(private val binding: BookmarkItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val title = binding.tvBookmarkItemTitle
        val description = binding.tvBookmarkItemDescription
        var isBookmarked = binding.switchBookmarkItemBookmark
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

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        holder.title.text = dataList[position].title
        holder.description.text = dataList[position].description
        holder.isBookmarked.isChecked = dataList[position].isBookmarked
    }
}