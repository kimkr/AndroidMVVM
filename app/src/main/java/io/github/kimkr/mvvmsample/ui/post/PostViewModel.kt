package io.github.kimkr.mvvmsample.ui.post

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import io.github.kimkr.mvvmsample.common.NetworkState
import io.github.kimkr.mvvmsample.persistence.model.Post

class PostViewModel(val networkState: LiveData<NetworkState>,
                    val postLiveData: LiveData<PagedList<Post>>) : ViewModel()
