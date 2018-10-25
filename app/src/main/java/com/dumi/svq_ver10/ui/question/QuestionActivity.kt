package com.dumi.svq_ver10.ui.question

import android.os.Bundle
import android.support.v4.app.Fragment
import com.dumi.svq_ver10.R
import com.dumi.svq_ver10.persistence.model.QuestionType
import com.dumi.svq_ver10.persistence.model.QuestionType.*
import com.dumi.svq_ver10.persistence.repository.QuestionRepository
import com.dumi.svq_ver10.ui.BaseActivity
import com.dumi.svq_ver10.ui.question.text.TextFragment
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class QuestionActivity : BaseActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>
    @field:[Inject]
    lateinit var questionRepository: QuestionRepository

    private lateinit var textFragment: TextFragment
    private lateinit var task: String
    private var questions = ArrayList<String>()

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
        questionRepository.getQuestionsByTask(task)
                .subscribe { questions ->
                    if (!questions.isEmpty()) {
                        questions.map { q -> this.questions.add(q.code) }
                        showQuestionFragment(questions[0]!!.code, questions[0]!!.type)
                    } else {
                        finish()
                    }
                }
    }

    fun nextQuestionOrFinish(finishedQuestion: String) {
        var nextQuestion = getNextQuestionId(finishedQuestion)
        if (nextQuestion != null) {
            questionRepository.getQuestionById(nextQuestion)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { nextQuestion -> showQuestionFragment(nextQuestion.code, nextQuestion.type) }
        } else {
            onBackPressed()
        }
    }

    private fun getNextQuestionId(thisQuestionId: String): String? {
        var idx = questions.indexOf(thisQuestionId)
        return if (idx + 1 < questions.size) questions[idx + 1] else null
    }

    private fun showQuestionFragment(questionId: String, type: QuestionType) {
        var bundle = Bundle()
        bundle.putString(BUNDLE_TASK_ID, task)
        bundle.putString(BUNDLE_QUESTION_ID, questionId)
        textFragment.arguments = bundle
        replaceFragment(R.id.fl_question_container, getFragment(type))
    }

    private fun getFragment(type: QuestionType): Fragment {
        return when (type) {
            TEXT -> textFragment
            RADIO -> textFragment
            SLIDE -> textFragment
            CHECKBOX -> textFragment
            TREE -> textFragment
        }
    }

    override fun onBackPressed() {
        finish()
    }

    companion object {
        const val BUNDLE_TASK_ID = "BUNDLE_TASK_ID "
        const val BUNDLE_QUESTION_ID = "BUNDLE_QUESTION_ID "
    }
}