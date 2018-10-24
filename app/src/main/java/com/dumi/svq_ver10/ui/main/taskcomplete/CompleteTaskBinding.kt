package com.dumi.svq_ver10.ui.main.taskcomplete

import android.databinding.BindingAdapter
import android.widget.ListView

object CompleteTaskBinding {
    @BindingAdapter("app:items")
    @JvmStatic
    fun setItems(listView: ListView, items: List<Any>) {
        with(listView.adapter as CompleteTaskAdapter) {
            replaceData(items)
        }
    }
}