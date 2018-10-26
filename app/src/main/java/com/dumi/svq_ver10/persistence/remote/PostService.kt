package com.dumi.svq_ver10.persistence.remote

import com.dumi.svq_ver10.persistence.model.Post
import com.dumi.svq_ver10.persistence.sources.PostDataSource
import io.reactivex.Flowable
import io.reactivex.Maybe
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostService @Inject constructor(private val postAPI: PostAPI) : PostDataSource {

    override fun getAllPosts(): Maybe<List<Post>> {
        return Maybe.empty()
    }

    override fun getPostById(id: String): Flowable<Post> {
        return Flowable.empty()
    }

    override fun getPostsByPage(page: Long, size: Int): Maybe<List<Post>> {
        return postAPI.getPostsByPage("movies", "079dac74a5f94ebdb990ecf61c8854b7", page, size)
                .map { it.articles }
    }

    override fun insertPost(post: Post) {
        return postAPI.insertPost(post)
    }

    override fun updatePost(post: Post) {
        return postAPI.updatePost(post)
    }

    override fun deleteAllPosts() {
        return postAPI.deleteAllPosts()
    }

    interface PostAPI {
        @GET("/v2/everything")
        fun getPostsByPage(@Query("q") q: String,
                           @Query("apiKey") apiKey: String,
                           @Query("page") page: Long,
                           @Query("pageSize") pageSize: Int): Maybe<Response>

        fun insertPost(post: Post)

        fun updatePost(post: Post)

        fun deleteAllPosts()
    }

    data class Response(val status: String, val totalResults: Long, val articles: List<Post>)
}

