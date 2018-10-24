package com.dumi.svq_ver10.ui.main.setting.location

import android.os.Bundle
import android.view.View
import com.dumi.svq_ver10.BR
import com.dumi.svq_ver10.R
import com.dumi.svq_ver10.ui.BaseFragment
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Named

class LocationFragment : BaseFragment(), View.OnClickListener {

    @field:[Inject Named("LocationViewModel")]
    lateinit var viewModel: LocationViewModel

    private val disposable = CompositeDisposable()

    override fun useDataBinding() = true

    override fun getLayout() = R.layout.fragment_setting_location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
    }

    override fun onStart() {
        super.onStart()
        binding.setVariable(BR.viewmodel, viewModel)
        binding.setVariable(BR.onClickListener, this)
//        disposable.add()
    }

    override fun onDestroy() {
        disposable.clear()
        super.onDestroy()
    }

    override fun onClick(view: View) {
//        (activity as MainActivity).addScreen(Screen.TASK_INCOMPLETE, view.tag as String)
    }

    companion object {
        val TAG = LocationFragment::class.java.simpleName
    }
}
