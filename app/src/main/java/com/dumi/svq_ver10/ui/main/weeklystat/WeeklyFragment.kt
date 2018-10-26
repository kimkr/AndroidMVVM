package com.dumi.svq_ver10.ui.main.weeklystat

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

class WeeklyFragment : BaseFragment(), View.OnClickListener {

    @field:[Inject Named("WeeklyViewModel")]
    lateinit var viewModel: WeeklyViewModel

    private val disposable = CompositeDisposable()

    override fun useDataBinding() = true

    override fun getLayout() = R.layout.fragment_weekly_stat

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
        (activity as MainActivity).addScreen(Screen.TASK_INCOMPLETE, view.tag as String)
    }

    companion object {
        val TAG = WeeklyFragment::class.java.simpleName
    }
}
