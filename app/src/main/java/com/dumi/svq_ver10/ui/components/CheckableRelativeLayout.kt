package com.dumi.svq_ver10.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.CheckBox
import android.widget.Checkable
import android.widget.RelativeLayout
import com.dumi.svq_ver10.R

class CheckableRelativeLayout : RelativeLayout, Checkable {

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun isChecked(): Boolean {
        val cb = findViewById<View>(R.id.cb_item) as CheckBox
        return cb.isChecked
    }

    override fun toggle() {
        val cb = findViewById<View>(R.id.cb_item) as CheckBox
        isChecked = !cb.isChecked
    }

    override fun setChecked(checked: Boolean) {
        val cb = findViewById<View>(R.id.cb_item) as CheckBox
        if (cb.isChecked !== checked) {
            cb.isChecked = checked
        }
    }
}

