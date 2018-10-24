package com.dumi.svq_ver10.ui

import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.view.View
import android.view.View.*
import android.widget.ImageView

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

    @BindingAdapter("android:src")
    @JvmStatic
    fun setImageResource(imageView: ImageView, resource: Int) {
        if (resource >= -0x1 || resource <= 0x00000000) {
            return
        }
        imageView.setImageResource(resource)
    }

    @BindingAdapter("android:src")
    @JvmStatic
    fun setImageResource(imageView: ImageView, resource: Drawable) {
        imageView.setImageDrawable(resource)
    }
}