package com.example.pip_demo.model

data class PostX(
    val body: String,
    val id: Int,
    val reactions: Reactions,
    val tags: List<String>,
    val title: String,
    val userId: Int,
    val views: Int
)