package com.mayukh.yourstore.models

import com.mayukh.yourstore.User

data class Post(
    var description: String = "",
    var imageUrl: String = "",
    var creationTime: Long = 0,
    var user: User? = null

)
