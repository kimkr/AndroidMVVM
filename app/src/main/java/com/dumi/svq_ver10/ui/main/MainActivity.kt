package com.dumi.svq_ver10.ui.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import com.dumi.svq_ver10.R
import com.dumi.svq_ver10.R.id.fl_main_container
import com.dumi.svq_ver10.ui.BaseActivity
import com.dumi.svq_ver10.ui.main.Screen.*
import com.dumi.svq_ver10.ui.main.home.HomeFragment
import com.dumi.svq_ver10.ui.main.setting.SettingFragment
import com.dumi.svq_ver10.ui.main.weeklystat.WeeklyFragment
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


class MainActivity : BaseActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    private lateinit var homeFragment: HomeFragment
    private lateinit var weeklyFragment: WeeklyFragment
    private lateinit var settingFragment: SettingFragment

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
        weeklyFragment = WeeklyFragment()
        settingFragment = SettingFragment()
        goTo(HOME)
    }

    fun goTo(screen: Screen) {
        if (screen.activity) {
            startActivity(screen)
        } else {
            replaceFragment(fl_main_container, getFragment(screen))
        }
    }

    private fun startActivity(screen: Screen) {
        when (screen) {
            SETTING_ACCOUNT -> Log.d(TAG, "startActivity $screen")
            SETTING_HEALING -> Log.d(TAG, "startActivity $screen")
            SETTING_LOCATION -> Log.d(TAG, "startActivity $screen")
        }
    }

    private fun getFragment(screen: Screen): Fragment {
        return when (screen) {
            HOME -> homeFragment
            WEEKLY_STAT -> weeklyFragment
            SETTING -> settingFragment
            else -> homeFragment
        }
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}