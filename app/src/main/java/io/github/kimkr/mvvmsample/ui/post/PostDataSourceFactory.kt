package io.github.kimkr.mvvmsample.ui.post

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import io.github.kimkr.mvvmsample.di.scopes.ActivityScope
import io.github.kimkr.mvvmsample.persistence.model.Post
import io.github.kimkr.mvvmsample.persistence.repository.PostRepository
import javax.inject.Inject

@ActivityScope
class PostDataSourceFactory @Inject constructor(private val postRepository: PostRepository)
    : DataSource.Factory<Long, Post>() {

    val mutableLiveData = MutableLiveData<PostDataSource>()
    lateinit var dataSource: PostDataSource

    override fun create(): DataSource<Long, Post> {
        dataSource = PostDataSource(postRepository)
        mutableLiveData.postValue(dataSource)
        return dataSource
    }
}