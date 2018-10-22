package com.dumi.svq_ver10.persistence.cache

import com.dumi.svq_ver10.persistence.model.Post
import com.dumi.svq_ver10.persistence.sources.PostDataSource
import io.reactivex.Flowable
import io.reactivex.Maybe
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostCache @Inject constructor() : PostDataSource {
    override fun getPostsByPage(page: Long, size: Int): Maybe<List<Post>> {
        return Maybe.empty()
    }

    override fun getAllPosts(): Maybe<List<Post>> {
        return Maybe.empty()
    }

    override fun getPostById(id: String): Flowable<Post> {
        return Flowable.empty()
    }

    override fun insertPost(post: Post) {
    }

    override fun updatePost(post: Post) {
    }

    override fun deleteAllPosts() {
    }

}