package com.dumi.svq_ver10

import android.databinding.BindingAdapter
import android.view.View
import android.view.View.*

object BaseBinding {

    @BindingAdapter("visibleOrGone")
    @JvmStatic
    fun setVisibleOrGone(view: View, visible: Boolean) {
        view.visibility = if (visible) VISIBLE else GONE
    }

    @BindingAdapter("visible")
    @JvmStatic
    fun setVisible(view: View, visible: Boolean) {
        view.visibility = if (visible) VISIBLE else INVISIBLE
    }
}