package com.dumi.svq_ver10.ui.post

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PageKeyedDataSource
import com.dumi.svq_ver10.common.NetworkState
import com.dumi.svq_ver10.persistence.model.Post
import com.dumi.svq_ver10.persistence.repository.PostRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class PostDataSource(private val postRepository: PostRepository) : PageKeyedDataSource<Long, Post>() {

    val networkState = MutableLiveData<NetworkState>()
    val initialLoading = MutableLiveData<NetworkState>()

    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Long, Post>) {
        initialLoading.postValue(NetworkState.RUNNING)
        networkState.postValue(NetworkState.RUNNING)
        postRepository.getPostsByPage(1, params.requestedLoadSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    callback.onResult(it, null, 21)
                    initialLoading.postValue(NetworkState.SUCCESS)
                    networkState.postValue(NetworkState.SUCCESS)
                }, {
                    initialLoading.postValue(NetworkState.FAILED)
                    networkState.postValue(NetworkState.FAILED)
                })
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, Post>) {
        networkState.postValue(NetworkState.RUNNING)
        postRepository.getPostsByPage(params.key, params.requestedLoadSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    callback.onResult(it, params.key + 1)
                    networkState.postValue(NetworkState.SUCCESS)
                }, {
                    networkState.postValue(NetworkState.FAILED)
                })
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, Post>) {
    }

    companion object {
        private val TAG = PostDataSource::class.java.simpleName
    }
}