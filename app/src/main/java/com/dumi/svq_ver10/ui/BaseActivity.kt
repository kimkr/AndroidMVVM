package com.dumi.svq_ver10.ui

import android.app.Fragment
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
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
import com.dumi.svq_ver10.R

abstract class BaseActivity : AppCompatActivity() {

    protected val BACK_PRESS_INTERVAL: Long = 2000
    protected val REQUEST_CODE = 1111
    protected var prevBackPressed: Long = 0
    protected lateinit var binding: ViewDataBinding

    abstract fun getLayout(): Int

    abstract fun useDataBinding(): Boolean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!useDataBinding()) {
            setContentView(getLayout())
        } else {
            binding = DataBindingUtil.setContentView<ViewDataBinding>(this, getLayout())
        }
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

    protected fun findFragment(id: Int): Fragment {
        return fragmentManager.findFragmentById(id)
    }

    protected fun findSupportFragment(id: Int): android.support.v4.app.Fragment {
        return supportFragmentManager.findFragmentById(id)
    }

    protected fun replaceFragment(layout: Int, fragment: Fragment) {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(layout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    protected fun replaceFragment(layout: Int, fragment: android.support.v4.app.Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(layout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    protected fun addFragment(layout: Int, fragment: Fragment) {
        val transaction = fragmentManager.beginTransaction()
        transaction.add(layout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    protected fun addFragment(layout: Int, fragment: android.support.v4.app.Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(layout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
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
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }

    protected fun navigateTo(target: Class<out Any>, key: String, value: String) {
        val intent = Intent(this, target)
        intent.putExtra(key, value)
        startActivityForResult(intent, REQUEST_CODE)
        finish()
    }

    protected fun hideKeyboard(et: EditText): Boolean {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(et.windowToken, 0)
        return true
    }

    protected fun onEnterHideKeyboard(et: EditText) {
        et.setOnEditorActionListener { _, action, _ ->
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
        animationSet.fillAfter = true
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

    companion object {
        const val BUNDLE_ARG = "BUNDLE_ARG"
    }
}