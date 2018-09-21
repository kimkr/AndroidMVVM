package io.github.kimkr.mvvmsample.ui.post

import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.content.Context
import android.support.v7.util.DiffUtil
import dagger.Module
import dagger.Provides
import io.github.kimkr.mvvmsample.di.scopes.ActivityScope
import io.github.kimkr.mvvmsample.persistence.model.Post


@Module
class PostModule {

    @Provides
    @ActivityScope
    fun providePagedListConfig(): PagedList.Config {
        return PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(10)
                .setPageSize(20)
                .build()
    }

    @Provides
    @ActivityScope
    fun provideViewModel(activity: PostActivity, viewModelFactory: PostViewModelFactory): PostViewModel {
        return ViewModelProviders.of(activity, viewModelFactory).get(PostViewModel::class.java)
    }

    @Provides
    @ActivityScope
    fun provideDiffUtilItemCallback(): DiffUtil.ItemCallback<Post> {
        return object : DiffUtil.ItemCallback<Post>() {
            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem.id === newItem.id
            }

            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem == newItem
            }
        }
    }

    @Provides
    @ActivityScope
    fun provideActivityAsContext(activity: PostActivity): Context {
        return activity
    }
}