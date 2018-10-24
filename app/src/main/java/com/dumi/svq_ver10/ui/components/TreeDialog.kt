package com.dumi.svq_ver10.ui.components

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.dumi.svq_ver10.R
import kotlinx.android.synthetic.main.dialog_tree.*

class TreeDialog(context: Context, var title: String, var singleListener: View.OnClickListener) :
        Dialog(context, android.R.style.Theme_Translucent_NoTitleBar) {

    override fun onCreate(savedInstanceState: Bundle) {
        val lpWindow = WindowManager.LayoutParams()
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        lpWindow.dimAmount = 0.8f
        window!!.attributes = lpWindow
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_single)
        tv_dialog_title.text = title
        btn_dialog_ok.setOnClickListener(singleListener)
    }
}