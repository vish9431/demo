package com.example.pip_demo.Repository

import com.example.pip_demo.Network.RetrofitInstance
import com.example.pip_demo.model.Post

class PostRepository {
    suspend fun getPost(): Post? {
        val response = RetrofitInstance.api.getPost()
        if (response.isSuccessful) {
            return response.body()
        }
        return null
    }
}