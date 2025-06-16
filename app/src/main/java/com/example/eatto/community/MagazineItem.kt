package com.example.eatto.community

data class MagazineItem(
    val nickname: String,
    val title: String,
    val imageUrl: String?,
    val timestamp: Long,
    val likes: Int,
    val comments: Int
)

