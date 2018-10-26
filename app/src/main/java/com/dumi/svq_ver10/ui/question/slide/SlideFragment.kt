package com.dumi.svq_ver10.ui.question.slide

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import com.dumi.svq_ver10.BR
import com.dumi.svq_ver10.R
import com.dumi.svq_ver10.ui.BaseFragment
import com.dumi.svq_ver10.ui.question.QuestionActivity
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_question_slide.*
import javax.inject.Inject
import javax.inject.Named

class SlideFragment : BaseFragment(), View.OnClickListener {

    @field:[Inject Named("SlideViewModel")]
    lateinit var viewModel: SlideViewModel
    lateinit var taskId: String
    lateinit var questionId: String

    private val disposable = CompositeDisposable()

    override fun useDataBinding() = true

    override fun getLayout() = R.layout.fragment_question_slide

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
        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                tv_slide_value.text = (progress + 1).toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })
        disposable.add(viewModel.loadQuestion(questionId))
    }

    override fun onDestroy() {
        disposable.clear()
        super.onDestroy()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_complete -> {
                viewModel.sendAnswer(seekbar.progress.toString())
                        .subscribe({
                            (activity as QuestionActivity).nextQuestionOrFinish(questionId)
                        }, { e ->
                            Log.e(TAG, e.message)
                        })
            }
        }
    }

    companion object {
        val TAG = SlideFragment::class.java.simpleName
    }
}