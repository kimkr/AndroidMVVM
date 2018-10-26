package com.dumi.svq_ver10.ui.main.selfcheck

import android.os.Bundle
import android.view.View
import com.dumi.svq_ver10.BR
import com.dumi.svq_ver10.ui.BaseFragment
import com.dumi.svq_ver10.R
import com.dumi.svq_ver10.ui.main.MainActivity
import com.dumi.svq_ver10.ui.main.Screen
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Named

class SelfCheckFragment : BaseFragment(), View.OnClickListener {

    @field:[Inject Named("SelfCheckViewModel")]
    lateinit var viewModel: SelfCheckViewModel

    private val disposable = CompositeDisposable()

    override fun useDataBinding() = true

    override fun getLayout() = R.layout.fragment_self_check

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
    }

    override fun onStart() {
        super.onStart()
        binding.setVariable(BR.viewmodel, viewModel)
        binding.setVariable(BR.onClickListener, this)
        disposable.add(viewModel.updateProgress())
    }

    override fun onDestroy() {
        disposable.clear()
        super.onDestroy()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_check_back -> viewModel.updateProgress(-1)
            R.id.btn_check_next -> viewModel.updateProgress(1)
            R.id.ll_check_vew_complete -> (activity as MainActivity).goTo(Screen.TASK_COMPLETE)
        }
    }

    companion object {
        val TAG = SelfCheckFragment::class.java.simpleName
    }
}
