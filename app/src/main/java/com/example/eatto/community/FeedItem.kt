package com.example.eatto.community

data class FeedItem(
    var nickname: String = "",
    var content: String = "",
    var likes: Int = 0,
    var comments: Int = 0,
    var imageUrl: String? = null,
    var timestamp: Long? = null,
    var id: String? = null
)




