package com.android.json

import retrofit2.http.GET

interface ApiService {
    @GET("posts")
    suspend fun getPost(): List<Post>
}