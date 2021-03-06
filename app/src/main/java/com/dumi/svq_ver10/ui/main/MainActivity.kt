package com.dumi.svq_ver10.ui.main

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.view.Gravity
import com.dumi.svq_ver10.R
import com.dumi.svq_ver10.persistence.repository.AuthRepository
import com.dumi.svq_ver10.ui.BaseActivity
import com.dumi.svq_ver10.ui.location.LocationActivity
import com.dumi.svq_ver10.ui.login.LoginActivity
import com.dumi.svq_ver10.ui.main.Screen.*
import com.dumi.svq_ver10.ui.main.home.HomeFragment
import com.dumi.svq_ver10.ui.main.profile.ProfileFragment
import com.dumi.svq_ver10.ui.main.selfcheck.SelfCheckFragment
import com.dumi.svq_ver10.ui.main.setting.SettingFragment
import com.dumi.svq_ver10.ui.main.setting.healing.HealingFragment
import com.dumi.svq_ver10.ui.main.setting.interval.IntervalFragment
import com.dumi.svq_ver10.ui.main.setting.location.LocationFragment
import com.dumi.svq_ver10.ui.main.taskcomplete.CompleteTaskFragment
import com.dumi.svq_ver10.ui.main.taskincomplete.IncompleteTaskFragment
import com.dumi.svq_ver10.ui.main.weeklystat.WeeklyFragment
import com.dumi.svq_ver10.ui.question.QuestionActivity
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_drawer.view.*
import kotlinx.android.synthetic.main.view_app_header.*
import javax.inject.Inject


class MainActivity : BaseActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>
    @field:[Inject]
    lateinit var authRepository: AuthRepository

    private lateinit var homeFragment: HomeFragment
    private lateinit var weeklyFragment: WeeklyFragment
    private lateinit var settingFragment: SettingFragment
    private lateinit var selfCheckFragment: SelfCheckFragment
    private lateinit var completeTaskFragment: CompleteTaskFragment
    private lateinit var incompleteTaskFragment: IncompleteTaskFragment
    private lateinit var settingHealingFragment: HealingFragment
    private lateinit var settingIntervalFragment: IntervalFragment
    private lateinit var settingLocationFragment: LocationFragment
    private lateinit var profileFragment: ProfileFragment
    private lateinit var screen: Screen

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
        selfCheckFragment = SelfCheckFragment()
        completeTaskFragment = CompleteTaskFragment()
        incompleteTaskFragment = IncompleteTaskFragment()
        settingHealingFragment = HealingFragment()
        settingIntervalFragment = IntervalFragment()
        settingLocationFragment = LocationFragment()
        profileFragment = ProfileFragment()
        initClickListener()
        goTo(HOME)
    }

    private fun initClickListener() {
        btn_ham.setOnClickListener { _ -> dl_main.openDrawer(Gravity.LEFT) }
        btn_logo.setOnClickListener { _ -> goTo(HOME) }
        val navigationView = (svq_ham_menu) as NavigationView
        val headerView = navigationView.getHeaderView(0)
        headerView.btn_setting.setOnClickListener { _ ->
            dl_main.closeDrawer(Gravity.LEFT)
            goTo(SETTING)
        }
        headerView.btn_noti_check.setOnClickListener { _ ->
            dl_main.closeDrawer(Gravity.LEFT)
            goTo(TASK_INCOMPLETE)
        }
        headerView.btn_self_check.setOnClickListener { _ ->
            dl_main.closeDrawer(Gravity.LEFT)
            goTo(SELF_CHECK)
        }
    }

    override fun onBackPressed() {
        val curTime = System.currentTimeMillis()
        val interval = curTime - prevBackPressed
        if (interval in 0..BACK_PRESS_INTERVAL) {
            moveTaskToBack(false)
            finish()
        } else {
            if (screen != HOME) {
                if (!supportFragmentManager.popBackStackImmediate()) {
                    goTo(HOME)
                }
            } else {
                prevBackPressed = curTime
                toast(R.string.back_button_exit_guide)
            }
        }
    }

    fun goTo(screen: Screen) {
        this.screen = screen
        if (screen.activity) {
            startActivity(screen, null)
        } else {
            replaceFragment(R.id.fl_main_container, getFragment(screen))
        }
    }

    fun addScreen(screen: Screen, arg: String) {
        this.screen = screen
        if (screen.activity) {
            startActivity(screen, arg)
        } else {
            var fragment = getFragment(screen)
            var bundle = Bundle()
            bundle.putString(BUNDLE_ARG, arg)
            fragment.arguments = bundle
            addFragment(R.id.fl_main_container, getFragment(screen))
        }
    }

    private fun startActivity(screen: Screen, arg: String?) {
        when (screen) {
            LOGIN -> {
                authRepository.setLogout()
                navigateTo(LoginActivity::class.java)
            }
            QUESTION -> {
                navigateTo(QuestionActivity::class.java, BUNDLE_ARG, arg!!)
            }
            LOCATION -> {
                navigateTo(LocationActivity::class.java)
            }
        }
    }

    private fun getFragment(screen: Screen): Fragment {
        return when (screen) {
            HOME -> homeFragment
            WEEKLY_STAT -> weeklyFragment
            SETTING -> settingFragment
            SELF_CHECK -> selfCheckFragment
            TASK_COMPLETE -> completeTaskFragment
            TASK_INCOMPLETE -> incompleteTaskFragment
            SETTING_INTERVAL -> settingIntervalFragment
            SETTING_HEALING -> settingHealingFragment
            SETTING_LOCATION -> settingLocationFragment
            PROFILE -> profileFragment
            else -> homeFragment
        }
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
//        val BUNDLE_ARG = "BUNDLE_ARG"
    }
}