package com.dumi.svq_ver10.ui.post

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.dumi.svq_ver10.R
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_post.*
import javax.inject.Inject


class PostActivity : AppCompatActivity() {
    @field:[Inject]
    lateinit var viewModel: PostViewModel
    @field:[Inject]
    lateinit var adapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        AndroidInjection.inject(this)
    }

    override fun onStart() {
        super.onStart()
        setAdapter()
    }

    private fun setAdapter() {
        list_post.layoutManager = LinearLayoutManager(applicationContext) as RecyclerView.LayoutManager?
        viewModel.postLiveData.observe(this, Observer { pagedList -> adapter.submitList(pagedList) })
        viewModel.networkState.observe(this, Observer { networkState -> adapter.setNetworkState(networkState!!) })
        list_post.adapter = adapter
    }

    companion object {
        private val TAG = PostActivity::class.java.simpleName
    }
}