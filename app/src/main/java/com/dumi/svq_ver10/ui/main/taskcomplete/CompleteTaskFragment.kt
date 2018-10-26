package com.dumi.svq_ver10.ui.main.taskcomplete

import android.os.Bundle
import android.view.View
import com.dumi.svq_ver10.BR
import com.dumi.svq_ver10.R
import com.dumi.svq_ver10.ui.BaseFragment
import com.dumi.svq_ver10.ui.main.MainActivity
import com.dumi.svq_ver10.ui.main.Screen
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_task_complete.*
import javax.inject.Inject
import javax.inject.Named

class CompleteTaskFragment : BaseFragment(), View.OnClickListener {

    @field:[Inject Named("CompleteTaskViewModel")]
    lateinit var viewModel: CompleteTaskViewModel
    @field:[Inject]
    lateinit var adapter: CompleteTaskAdapter

    private val disposable = CompositeDisposable()

    override fun useDataBinding() = true

    override fun getLayout() = R.layout.fragment_task_complete

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
    }

    override fun onStart() {
        super.onStart()
        binding.setVariable(BR.viewmodel, viewModel)
        binding.setVariable(BR.onClickListener, this)
        lv_task_complete.adapter = adapter
        disposable.add(viewModel.loadMontlyCompleteTasks())
    }

    override fun onDestroy() {
        disposable.clear()
        super.onDestroy()
    }

    override fun onClick(view: View) {
        if (view.tag is Screen) {
            (activity as MainActivity).goTo(view.tag as Screen)
        }
        when (view.id) {
            R.id.btn_check_back -> viewModel.loadMontlyCompleteTasks(-1)
            R.id.btn_check_next -> viewModel.loadMontlyCompleteTasks(1)
        }
    }

    companion object {
        val TAG = CompleteTaskFragment::class.java.simpleName
    }
}
