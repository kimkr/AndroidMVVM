package io.github.kimkr.mvvmsample.persistence.sources

import io.github.kimkr.mvvmsample.persistence.model.Post
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