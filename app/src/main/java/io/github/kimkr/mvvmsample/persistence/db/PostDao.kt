package io.github.kimkr.mvvmsample.persistence.db

import android.arch.persistence.room.*
import io.github.kimkr.mvvmsample.persistence.model.Post
import io.github.kimkr.mvvmsample.persistence.sources.PostDataSource
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
interface PostDao : PostDataSource {

    @Query("SELECT * FROM Posts LIMIT :size OFFSET :page")
    override fun getPostsByPage(page: Long, size: Int): Maybe<List<Post>>

    @Query("SELECT * FROM Posts")
    override fun getAllPosts(): Maybe<List<Post>>

    @Query("SELECT * FROM Posts WHERE id = :id")
    override fun getPostById(id: String): Flowable<Post>

    @Insert(onConflict = OnConflictStrategy.FAIL)
    override fun insertPost(post: Post)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    override fun updatePost(post: Post)

    @Query("DELETE FROM Posts")
    override fun deleteAllPosts()
}