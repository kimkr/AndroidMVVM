package com.dumi.svq_ver10.ui.post

import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.dumi.svq_ver10.di.scopes.ActivityScope
import java.util.concurrent.Executor
import javax.inject.Inject

@ActivityScope
class PostViewModelFactory @Inject constructor(
        private val executor: Executor,
        private val postDataSourceFactory: PostDataSourceFactory,
        private val pagedListConfig: PagedList.Config)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostViewModel::class.java)) {
            var postLiveData = LivePagedListBuilder(postDataSourceFactory, pagedListConfig)
                    .setFetchExecutor(executor)
                    .build()
            var networkState = Transformations.switchMap(postDataSourceFactory.mutableLiveData)
            { dataSource -> dataSource.networkState }
            return PostViewModel(networkState, postLiveData) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}