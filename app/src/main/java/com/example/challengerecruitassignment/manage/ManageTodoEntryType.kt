package com.example.challengerecruitassignment.manage

enum class ManageTodoEntryType {
    CREATE,
    UPDATE,
    DELETE;

    companion object {
        fun getEntryType(
            ordinal: Int?
        ): ManageTodoEntryType {
            return entries.firstOrNull() {
                it.ordinal == ordinal
            } ?: CREATE
        }
    }
}