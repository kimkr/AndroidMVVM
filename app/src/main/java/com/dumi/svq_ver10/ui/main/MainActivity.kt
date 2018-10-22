package com.dumi.svq_ver10.ui.main

import com.dumi.svq_ver10.R
import com.dumi.svq_ver10.ui.BaseActivity

class MainActivity : BaseActivity() {

    override fun getLayout(): Int {
        return R.layout.activity_splash
    }

    override fun useDataBinding(): Boolean {
        return false
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}