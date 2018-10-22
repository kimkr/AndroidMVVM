package com.dumi.svq_ver10.ui.post

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import com.dumi.svq_ver10.common.NetworkState
import com.dumi.svq_ver10.persistence.model.Post

class PostViewModel(val networkState: LiveData<NetworkState>,
                    val postLiveData: LiveData<PagedList<Post>>) : ViewModel()
