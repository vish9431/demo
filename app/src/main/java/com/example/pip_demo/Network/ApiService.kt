package com.example.pip_demo.Network

import com.example.pip_demo.model.Post
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("/posts")
    suspend fun getPost(): Response<Post>
}
