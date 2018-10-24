package com.dumi.svq_ver10.ui.main.taskincomplete

import android.os.Bundle
import android.util.Log
import android.view.View
import com.dumi.svq_ver10.BR
import com.dumi.svq_ver10.R
import com.dumi.svq_ver10.ui.BaseFragment
import com.dumi.svq_ver10.ui.main.MainActivity
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_task_incomplete.*
import javax.inject.Inject
import javax.inject.Named

class IncompleteTaskFragment : BaseFragment(), View.OnClickListener {

    @field:[Inject Named("IncompleteTaskViewModel")]
    lateinit var viewModel: IncompleteTaskViewModel
    @field:[Inject]
    lateinit var adapter: IncompleteTaskAdapter

    private val disposable = CompositeDisposable()

    override fun useDataBinding() = true

    override fun getLayout() = R.layout.fragment_task_incomplete

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
    }

    override fun onStart() {
        super.onStart()
        binding.setVariable(BR.viewmodel, viewModel)
        binding.setVariable(BR.onClickListener, this)
        lv_task_incomplete.setOnItemClickListener { _, _, i, l ->
            var task = adapter.getItem(i)
            Log.d(TAG, "onClick task ${task}")
        }
        lv_task_incomplete.adapter = adapter
        Log.d(TAG, "onStart argument ${getArgument(MainActivity.BUNDLE_ARG)}")
        disposable.add(viewModel.loadTasks())
    }

    override fun onDestroy() {
        disposable.clear()
        super.onDestroy()
    }

    override fun onClick(view: View) {
    }

    companion object {
        val TAG = IncompleteTaskFragment::class.java.simpleName
    }
}
