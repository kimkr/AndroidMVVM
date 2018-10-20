package io.github.kimkr.mvvmsample.ui

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.AnimRes
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import io.github.kimkr.mvvmsample.R

abstract class BaseActivity : AppCompatActivity() {

    private val BACK_PRESS_INTERVAL: Long = 2000
    private var prevBackPressed: Long = 0

    abstract fun getLayout(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
    }

    override fun onBackPressed() {
        val curTime = System.currentTimeMillis()
        val interval = curTime - prevBackPressed
        if (interval in 0..BACK_PRESS_INTERVAL) {
            moveTaskToBack(false)
            finish()
        } else {
            prevBackPressed = curTime
            toast(R.string.back_button_exit_guide)
        }
    }

    protected fun toast(str: Int) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
    }

    protected fun toast(str: String) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
    }

    protected fun showLoading(title: String, message: String): ProgressDialog {
        val pd = ProgressDialog.show(this, title, message)
        pd.setCancelable(true)
        return pd
    }

    protected fun showLoading(title: Int, message: Int): ProgressDialog {
        val pd = ProgressDialog.show(this, this.getString(title), this.getString(message))
        pd.setCancelable(true)
        return pd
    }


    protected fun navigateTo(target: Class<out Any>) {
        val intent = Intent(this, target)
        startActivity(intent)
        finish()
    }

    protected fun hideKeyboard(et: EditText) : Boolean {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(et.windowToken, 0)
        return true
    }

    protected fun onEnterHideKeyboard(et: EditText) {
        et.setOnEditorActionListener { tv, action, event ->
                when (action) {
                    EditorInfo.IME_ACTION_DONE -> hideKeyboard(et)
                    else -> false
                }
        }
    }

    protected fun animateViewComponent(view: View, @AnimRes animRes: Int) {
        val anim = AnimationUtils.loadAnimation(applicationContext, animRes)
        if (view.visibility != View.VISIBLE) {
            view.visibility = View.VISIBLE
        }
        view.startAnimation(anim)
    }

    protected fun animateViewComponent(view: View, animList: List<Int>, startOffset: Int,
                                       duration: Int, onComplete: () -> Unit) {
        val animationSet = AnimationSet(true)
        animList.map { animRes -> animationSet.addAnimation(AnimationUtils.loadAnimation(applicationContext, animRes)) }
        animationSet.startOffset = startOffset.toLong()
        animationSet.duration = duration.toLong()
        animationSet.restrictDuration(duration.toLong())
        animationSet.setAnimationListener(AnimationEndListener(onComplete))
        view.startAnimation(animationSet)
    }

    private class AnimationEndListener(var onComplete: () -> Unit) : Animation.AnimationListener {

        override fun onAnimationStart(animation: Animation) {
        }

        override fun onAnimationEnd(animation: Animation) {
            onComplete()
        }

        override fun onAnimationRepeat(animation: Animation) {
        }
    }
}