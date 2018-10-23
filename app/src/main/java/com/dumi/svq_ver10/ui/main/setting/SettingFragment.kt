package com.dumi.svq_ver10.ui.main.setting

import android.os.Bundle
import android.view.View
import com.dumi.svq_ver10.BR
import com.dumi.svq_ver10.BaseFragment
import com.dumi.svq_ver10.R
import com.dumi.svq_ver10.ui.main.MainActivity
import com.dumi.svq_ver10.ui.main.Screen
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Named

class SettingFragment : BaseFragment(), View.OnClickListener {

    @field:[Inject Named("SettingViewModel")]
    lateinit var viewModel: SettingViewModel

    private val disposable = CompositeDisposable()

    override fun useDataBinding() = true

    override fun getLayout() = R.layout.fragment_setting

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
    }

    override fun onStart() {
        super.onStart()
        binding.setVariable(BR.viewmodel, viewModel)
        binding.setVariable(BR.onClickListener, this)
    }

    override fun onDestroy() {
        disposable.clear()
        super.onDestroy()
    }

    override fun onClick(view: View) {
        if (view.tag is Screen) {
            (activity as MainActivity).goTo(view.tag as Screen)
        }
    }

    companion object {
        val TAG = SettingFragment::class.java.simpleName
    }
}
