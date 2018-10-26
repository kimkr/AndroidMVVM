package com.dumi.svq_ver10.ui.location

import android.databinding.BindingAdapter
import android.widget.ListView
import com.dumi.svq_ver10.util.location.GoogleMapService

object SearchResultBinding {
    @BindingAdapter("app:items")
    @JvmStatic
    fun setItems(listView: ListView, items: List<GoogleMapService.Result>) {
        with(listView.adapter as SearchResultAdapter) {
            replaceData(items)
        }
    }
}