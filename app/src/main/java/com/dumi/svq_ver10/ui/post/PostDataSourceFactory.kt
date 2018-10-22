package com.dumi.svq_ver10.ui.post

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.dumi.svq_ver10.di.scopes.ActivityScope
import com.dumi.svq_ver10.persistence.model.Post
import com.dumi.svq_ver10.persistence.repository.PostRepository
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