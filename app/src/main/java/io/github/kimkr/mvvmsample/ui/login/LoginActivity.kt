package io.github.kimkr.mvvmsample.ui.login

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import io.github.kimkr.mvvmsample.R
import io.github.kimkr.mvvmsample.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    override fun getLayout(): Int {
        return R.layout.activity_login
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btn_login.setOnClickListener { _ -> onClickSend() }
        ll_login_root.setOnClickListener { _ -> hideKeyboard(et_login_code) }
        onEnterHideKeyboard(et_login_code)
    }

    private fun onClickSend() {
        hideKeyboard(et_login_code)
        val userId = et_login_code.text.toString()
        if (TextUtils.isEmpty(userId)) {
            toast(R.string.login_enter_code_guide)
            return
        }
        val dialog = showLoading(R.string.loading, R.string.loading_login)
        Log.d(TAG, userId)
    }

    override fun onResume() {
        super.onResume()
        showViewComponents()
    }

    private fun showViewComponents() {
        animateViewComponent(tv_login_greeting, R.anim.alpha)
        animateViewComponent(tv_login_code_guide, R.anim.alpha2)
        animateViewComponent(btn_login, R.anim.alpha3)
        animateViewComponent(et_login_code, R.anim.alpha3)
        animateViewComponent(cb_login_auto, R.anim.alpha3)
    }

    companion object {
        private val TAG = LoginActivity::class.java.simpleName
    }
}