package com.dumi.svq_ver10.persistence.repository

import com.dumi.svq_ver10.di.qualifier.Cache
import com.dumi.svq_ver10.di.qualifier.Local
import com.dumi.svq_ver10.di.qualifier.Remote
import com.dumi.svq_ver10.persistence.model.Post
import com.dumi.svq_ver10.persistence.sources.PostDataSource
import io.reactivex.Flowable
import io.reactivex.Maybe
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepository @Inject constructor(@Local private val localDataSource: PostDataSource,
                                         @Remote private val remoteDataSource: PostDataSource,
                                         @Cache private val cacheDataSource: PostDataSource)
    : PostDataSource, Repository {
    override fun getPostsByPage(page: Long, size: Int): Maybe<List<Post>> {
        return remoteDataSource.getPostsByPage(page, size)
    }

    override fun getAllPosts(): Maybe<List<Post>> {
        return localDataSource.getAllPosts()
    }

    override fun getPostById(id: String): Flowable<Post> {
        return localDataSource.getPostById(id)
    }

    override fun insertPost(post: Post) {
        localDataSource.insertPost(post)
    }

    override fun updatePost(post: Post) {
        localDataSource.updatePost(post)
    }

    override fun deleteAllPosts() {
    }
}