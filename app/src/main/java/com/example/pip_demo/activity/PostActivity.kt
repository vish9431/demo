package com.example.pip_demo.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pip_demo.Adapter.PostAdapter
import com.example.pip_demo.R
import com.example.pip_demo.viewModel.PostViewModel

class PostActivity : AppCompatActivity() {
    private lateinit var postAdapter: PostAdapter
    private val postViewModel : PostViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewPosts)
        recyclerView.layoutManager = LinearLayoutManager(this)

        postViewModel.posts.observe(this, Observer { posts ->
            postAdapter = PostAdapter(posts)
            recyclerView.adapter = postAdapter
        })
    postViewModel.fetchPosts()

    }
}