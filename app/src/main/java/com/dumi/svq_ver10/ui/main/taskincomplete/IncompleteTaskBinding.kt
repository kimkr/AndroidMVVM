package com.dumi.svq_ver10.ui.main.taskincomplete

import android.databinding.BindingAdapter
import android.widget.ListView
import com.dumi.svq_ver10.persistence.model.Task

object IncompleteTaskBinding {
    @BindingAdapter("app:items")
    @JvmStatic
    fun setItems(listView: ListView, items: List<Task>) {
        with(listView.adapter as IncompleteTaskAdapter) {
            replaceData(items)
        }
    }
}