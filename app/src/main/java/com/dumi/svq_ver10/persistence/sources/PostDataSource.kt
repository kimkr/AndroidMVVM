package com.dumi.svq_ver10.persistence.sources

import com.dumi.svq_ver10.persistence.model.Post
import io.reactivex.Flowable
import io.reactivex.Maybe

interface PostDataSource {

    fun getAllPosts(): Maybe<List<Post>>

    fun getPostById(id: String): Flowable<Post>

    fun getPostsByPage(page: Long, size: Int): Maybe<List<Post>>

    fun insertPost(post: Post)

    fun updatePost(post: Post)

    fun deleteAllPosts()
}