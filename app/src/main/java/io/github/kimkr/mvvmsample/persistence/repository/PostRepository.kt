package io.github.kimkr.mvvmsample.persistence.repository

import io.github.kimkr.mvvmsample.di.qualifier.Cache
import io.github.kimkr.mvvmsample.di.qualifier.Local
import io.github.kimkr.mvvmsample.di.qualifier.Remote
import io.github.kimkr.mvvmsample.persistence.model.Post
import io.github.kimkr.mvvmsample.persistence.sources.PostDataSource
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