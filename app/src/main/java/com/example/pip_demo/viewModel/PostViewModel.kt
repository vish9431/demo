package com.example.pip_demo.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pip_demo.Repository.PostRepository
import com.example.pip_demo.model.Post
import com.example.pip_demo.model.PostX
import kotlinx.coroutines.launch

class PostViewModel : ViewModel() {
    private val repository = PostRepository()
    private val _posts = MutableLiveData<List<PostX>>()
    val posts: LiveData<List<PostX>> get() = _posts

    fun fetchPosts() {
        viewModelScope.launch {
            val postResponse = repository.getPost()
            if (postResponse != null) {
                _posts.postValue(postResponse.posts)
            }
        }
    }
}