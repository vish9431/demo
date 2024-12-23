package com.example.pip_demo.model

data class Post(
    val limit: Int,
    val posts: List<PostX>,
    val skip: Int,
    val total: Int
)