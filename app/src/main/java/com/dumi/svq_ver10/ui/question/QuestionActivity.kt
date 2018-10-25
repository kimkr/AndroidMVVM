package com.dumi.svq_ver10.ui.question

import android.os.Bundle
import android.support.v4.app.Fragment
import com.dumi.svq_ver10.R
import com.dumi.svq_ver10.ui.BaseActivity
import com.dumi.svq_ver10.ui.question.text.TextFragment
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class QuestionActivity : BaseActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    private lateinit var textFragment: TextFragment
    private lateinit var task: String

    override fun getLayout() = R.layout.activity_question
    override fun useDataBinding() = false
    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        task = intent.getStringExtra(BaseActivity.BUNDLE_ARG)
        textFragment = TextFragment()
    }

    override fun onStart() {
        super.onStart()
        var bundle = Bundle()
        bundle.putString(BUNDLE_ARG, task)
        textFragment.arguments = bundle
        replaceFragment(R.id.fl_question_container, getFragment("text"))
    }

    private fun getFragment(type: String): Fragment {
        return textFragment
    }

    override fun onBackPressed() {
        setResult(REQUEST_CODE, null)
        finish()
    }
}