package com.dumi.svq_ver10.ui.popup

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.dumi.svq_ver10.R
import com.dumi.svq_ver10.ui.components.TreeDialog

class PopupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var dialog = TreeDialog(this,
                this.getString(R.string.new_message_title),
                View.OnClickListener {
                    finish()
                })
        dialog.show()
    }
}
