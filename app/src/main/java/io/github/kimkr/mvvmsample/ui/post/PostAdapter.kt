package io.github.kimkr.mvvmsample.ui.post

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.kimkr.mvvmsample.R
import io.github.kimkr.mvvmsample.common.NetworkState
import io.github.kimkr.mvvmsample.di.scopes.ActivityScope
import io.github.kimkr.mvvmsample.persistence.model.Post
import kotlinx.android.synthetic.main.item_post_content.view.*
import kotlinx.android.synthetic.main.item_post_network.view.*
import javax.inject.Inject


@ActivityScope
class PostAdapter @Inject constructor(context: Context, diffCallback: DiffUtil.ItemCallback<Post>)
    : PagedListAdapter<Post, RecyclerView.ViewHolder>(diffCallback) {

    private val TYPE_PROGRESS = 0
    private val TYPE_ITEM = 1
    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.getContext())
        if (viewType === TYPE_PROGRESS) {
            val itemView = layoutInflater.inflate(R.layout.item_post_network, parent, false)
            return NetworkStateItemViewHolder(itemView)

        } else {
            val itemView = layoutInflater.inflate(R.layout.item_post_content, parent, false)
            return PostItemViewHolder(itemView)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            TYPE_PROGRESS
        } else {
            TYPE_ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PostItemViewHolder) {
            (holder as PostItemViewHolder).bind(getItem(position)!!)
        } else {
            (holder as NetworkStateItemViewHolder).bind(networkState!!)
        }
    }

    fun setNetworkState(newNetworkState: NetworkState) {
        val previousState = this.networkState
        val previousExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val newExtraRow = hasExtraRow()
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(itemCount)
            } else {
                notifyItemInserted(itemCount)
            }
        } else if (newExtraRow && previousState !== newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    private fun hasExtraRow(): Boolean {
        return if (networkState != null && networkState !== NetworkState.RUNNING) {
            true
        } else {
            false
        }
    }

    class PostItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(post: Post) {
            itemView.item_title.text = post.title
            itemView.item_time.text = post.publishedAt
            itemView.item_desc.text = post.description
            itemView.item_comments.text = post.content
        }
    }

    class NetworkStateItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(networkState: NetworkState) {
            if (networkState != null && networkState == NetworkState.RUNNING) {
                itemView.progress_bar.visibility = View.VISIBLE
            } else {
                itemView.progress_bar.visibility = View.GONE
            }
            if (networkState != null && networkState == NetworkState.FAILED) {
                itemView.error_msg.visibility = View.VISIBLE
                itemView.error_msg.text = "Network Error"
            } else {
                itemView.error_msg.visibility = View.GONE
            }
        }
    }
}