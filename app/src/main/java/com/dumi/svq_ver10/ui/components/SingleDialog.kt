package com.dumi.svq_ver10.ui.components

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.dumi.svq_ver10.R
import kotlinx.android.synthetic.main.dialog_single.*

class SingleDialog(context: Context,
                   private val title: String,
                   private val onClickListener: View.OnClickListener?) :
        Dialog(context, android.R.style.Theme_Translucent_NoTitleBar) {

    override fun onCreate(savedInstanceState: Bundle?) {
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        layoutParams.dimAmount = 0.8f
        window!!.attributes = layoutParams
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_single)
        tv_title.text = title
        if (onClickListener != null) {
            btn_ok!!.setOnClickListener(onClickListener)
        }
    }
}
