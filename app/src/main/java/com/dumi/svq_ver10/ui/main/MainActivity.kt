package com.dumi.svq_ver10.ui.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import com.dumi.svq_ver10.R
import com.dumi.svq_ver10.R.id.fl_main_container
import com.dumi.svq_ver10.ui.BaseActivity
import com.dumi.svq_ver10.ui.main.Screen.HOME
import com.dumi.svq_ver10.ui.main.home.HomeFragment
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


class MainActivity : BaseActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    private lateinit var homeFragment: HomeFragment

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun useDataBinding(): Boolean {
        return false
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        homeFragment = HomeFragment()
        replaceFragment(HOME)
    }

    fun replaceFragment(screen: Screen) {
        Log.d(TAG, "replaceFragment $screen")
        replaceFragment(fl_main_container, getFragment(screen))
    }

    private fun getFragment(screen: Screen): Fragment {
        return when (screen) {
            HOME -> homeFragment
            else -> homeFragment
        }
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}