package com.dumi.svq_ver10.ui.login

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.dumi.svq_ver10.R
import com.dumi.svq_ver10.persistence.repository.UserRepository
import com.dumi.svq_ver10.ui.BaseActivity
import com.dumi.svq_ver10.ui.location.LocationActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : BaseActivity() {
    @field:[Inject]
    lateinit var userRepository: UserRepository

    override fun getLayout(): Int {
        return R.layout.activity_login
    }

    override fun useDataBinding(): Boolean {
      return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
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
        userRepository.getUserById(userId)
                .subscribe(
                        { user ->
                            Log.d(TAG, user.toString())
                            dialog.cancel()
                            navigateTo(LocationActivity::class.java)
                        },
                        { error ->
                            dialog.cancel()
                            Log.e(TAG, "error $error")
                        })
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