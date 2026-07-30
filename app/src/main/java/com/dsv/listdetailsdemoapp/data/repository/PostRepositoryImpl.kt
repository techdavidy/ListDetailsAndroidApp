package com.dsv.listdetailsdemoapp.data.repository

import com.dsv.listdetailsdemoapp.data.model.Post
import com.dsv.listdetailsdemoapp.data.network.ApiService
import javax.inject.Inject

class PostRepositoryImpl
    @Inject
    constructor(
        private val apiService: ApiService,
    ) : PostRepository {
        override suspend fun fetchPosts(): List<Post> = apiService.getPosts()

        override suspend fun fetchPostDetail(id: Int): Post = apiService.getPostById(id)
    }
