package com.dsv.listdetailsdemoapp.data.repository

import com.dsv.listdetailsdemoapp.data.model.Post

interface PostRepository {
    suspend fun fetchPosts(): List<Post>

    suspend fun fetchPostDetail(id: Int): Post
}
