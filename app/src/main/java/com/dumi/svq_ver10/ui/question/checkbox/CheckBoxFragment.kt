package com.dumi.svq_ver10.ui.question.checkbox

import android.os.Bundle
import android.util.Log
import android.view.View
import com.dumi.svq_ver10.BR
import com.dumi.svq_ver10.R
import com.dumi.svq_ver10.ui.BaseFragment
import com.dumi.svq_ver10.ui.question.QuestionActivity
import com.dumi.svq_ver10.ui.question.slide.SlideFragment
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_question_checkbox.*
import javax.inject.Inject
import javax.inject.Named

class CheckBoxFragment : BaseFragment(), View.OnClickListener {

    @field:[Inject Named("CheckBoxViewModel")]
    lateinit var viewModel: CheckBoxViewModel
    @field:[Inject]
    lateinit var adapter: CheckBoxAdapter

    lateinit var taskId: String
    lateinit var questionId: String

    private val selectedPosition = HashSet<Int>()
    private val disposable = CompositeDisposable()

    override fun useDataBinding() = true

    override fun getLayout() = R.layout.fragment_question_checkbox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
    }

    override fun onStart() {
        super.onStart()
        binding.setVariable(BR.viewmodel, viewModel)
        binding.setVariable(BR.onClickListener, this)
        taskId = getArgument(QuestionActivity.BUNDLE_TASK_ID)!!
        questionId = getArgument(QuestionActivity.BUNDLE_QUESTION_ID)!!
        ll_checkbox.adapter = adapter
        ll_checkbox.setOnItemClickListener { _, v, i, l ->
            if (selectedPosition.contains(i)) {
                selectedPosition.remove(i)
            } else {
                selectedPosition.add(i)
            }
        }
        disposable.add(viewModel.loadQuestion(questionId))
    }

    override fun onDestroy() {
        disposable.clear()
        super.onDestroy()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_complete -> {
                var answer = viewModel.formatAnswer(selectedPosition)
                Log.d(TAG, "btn_complete answer: $answer, selectedPosition: $selectedPosition")
                if (answer.isNullOrEmpty()) {
                    (activity as QuestionActivity).toast(R.string.nothing_selected_error)
                    return
                }
                viewModel.sendAnswer(answer)
                        .subscribe({
                            (activity as QuestionActivity).nextQuestionOrFinish(questionId)
                        }, { e ->
                            Log.e(SlideFragment.TAG, e.message)
                        })
            }
        }
    }

    companion object {
        val TAG = CheckBoxFragment::class.java.simpleName
    }
}