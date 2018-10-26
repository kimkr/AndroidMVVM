package com.dumi.svq_ver10.ui.question.text

import android.os.Bundle
import android.util.Log
import android.view.View
import com.dumi.svq_ver10.BR
import com.dumi.svq_ver10.R
import com.dumi.svq_ver10.ui.BaseFragment
import com.dumi.svq_ver10.ui.question.QuestionActivity
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_question_text.*
import javax.inject.Inject
import javax.inject.Named


class TextFragment : BaseFragment(), View.OnClickListener {

    @field:[Inject Named("TextViewModel")]
    lateinit var viewModel: TextViewModel
    lateinit var taskId: String
    lateinit var questionId: String

    private val disposable = CompositeDisposable()


    override fun useDataBinding() = true

    override fun getLayout() = R.layout.fragment_question_text

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
        disposable.add(viewModel.loadQuestion(questionId))
    }

    override fun onDestroy() {
        disposable.clear()
        super.onDestroy()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_complete -> {
                viewModel.sendAnswer(et_question_text.text.toString())
                        .subscribe({
                            (activity as QuestionActivity).nextQuestionOrFinish(questionId)
                        }, { e ->
                            Log.e(TAG, e.message)
                        })
            }
        }
    }

    companion object {
        val TAG = TextFragment::class.java.simpleName
    }
}