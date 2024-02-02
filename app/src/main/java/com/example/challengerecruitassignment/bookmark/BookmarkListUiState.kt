package com.example.challengerecruitassignment.bookmark

data class BookmarkListUiState(
    val bookmarkList: List<BookmarkListItem>
) {
    companion object {
        fun init() = BookmarkListUiState(
            bookmarkList = emptyList()
        )
    }
}
