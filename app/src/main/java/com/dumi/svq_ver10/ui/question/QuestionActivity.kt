package com.dumi.svq_ver10.ui.question

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import com.dumi.svq_ver10.R
import com.dumi.svq_ver10.persistence.model.QuestionType
import com.dumi.svq_ver10.persistence.model.QuestionType.*
import com.dumi.svq_ver10.persistence.repository.QuestionRepository
import com.dumi.svq_ver10.ui.BaseActivity
import com.dumi.svq_ver10.ui.location.LocationActivity
import com.dumi.svq_ver10.ui.main.MainActivity
import com.dumi.svq_ver10.ui.question.checkbox.CheckBoxFragment
import com.dumi.svq_ver10.ui.question.slide.SlideFragment
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
    @field:[Inject]
    lateinit var viewModel: QuestionViewModel

    lateinit var task: String
    private var questions = ArrayList<String>()
    private var textFragment: TextFragment? = null
    private var slideFragment: SlideFragment? = null
    private var checkBoxFragment: CheckBoxFragment? = null

    override fun getLayout() = R.layout.activity_question
    override fun useDataBinding() = false
    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        task = intent.getStringExtra(BaseActivity.BUNDLE_ARG)
    }

    override fun onStart() {
        super.onStart()
        questionRepository.getQuestionsByTask(task)
                .subscribe { questions ->
                    if (!questions.isEmpty()) {
                        questions.map { q -> this.questions.add(q.code) }
                        showQuestion(questions[0]!!.code, questions[0]!!.type)
                    } else {
                        finish()
                    }
                }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && data != null) {
            var question = data!!.getStringExtra(BUNDLE_QUESTION_ID)
            var latitude = data!!.getDoubleExtra(BUNDLE_ARG_LAT, 0.0)
            var longitude = data!!.getDoubleExtra(BUNDLE_ARG_LON, 0.0)
            var address = data!!.getStringExtra(BUNDLE_ARG_ADDR)
            Log.d(TAG, "onActivityResult question: $question, task: $task, latitude : $latitude, " +
                    "longitude : $longitude, address : $address")
            viewModel.sendAnswer(task, question, LOCATION, "$latitude-$longitude")
                    .subscribe { ret ->
                        questionRepository.getQuestionsByTask(task)
                                .subscribe { questions ->
                                    if (!questions.isEmpty()) {
                                        this.questions.clear()
                                        questions.map { q -> this.questions.add(q.code) }
                                        nextQuestionOrFinish(question)
                                    } else {
                                        finish()
                                    }
                                }
                    }
        }
    }

    fun nextQuestionOrFinish(finishedQuestion: String) {
        var nextQuestion = getNextQuestionId(finishedQuestion)
        Log.d(TAG, "nextQuestion: $nextQuestion")
        if (nextQuestion != null) {
            questionRepository.getQuestionById(nextQuestion)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { nextQuestion -> showQuestion(nextQuestion.code, nextQuestion.type) }
        } else {
            navigateTo(MainActivity::class.java)
            finish()
        }
    }

    private fun getNextQuestionId(thisQuestionId: String): String? {
        var idx = questions.indexOf(thisQuestionId)
        return if (idx + 1 < questions.size) questions[idx + 1] else null
    }

    private fun showQuestion(questionId: String, type: QuestionType) {
        var fragment = getFragment(type)
        if (fragment == null) {
            when (type) {
                LOCATION -> navigateTo(LocationActivity::class.java, BUNDLE_QUESTION_ID, questionId)
            }
        } else {
            var bundle = Bundle()
            bundle.putString(BUNDLE_TASK_ID, task)
            bundle.putString(BUNDLE_QUESTION_ID, questionId)
            fragment.arguments = bundle
            replaceFragment(R.id.fl_question_container, fragment)
        }
    }

    private fun getFragment(type: QuestionType): Fragment? {
        return when (type) {
            TEXT -> {
                if (textFragment == null)
                    textFragment = TextFragment()
                textFragment
            }
            SLIDE -> {
                if (slideFragment == null)
                    slideFragment = SlideFragment()
                slideFragment
            }
            RADIO, CHECKBOX, TREE -> {
                if (checkBoxFragment == null)
                    checkBoxFragment = CheckBoxFragment()
                checkBoxFragment
            }
            LOCATION -> {
                null
            }
        }
    }

    override fun onBackPressed() {
        finish()
    }


    companion object {
        var TAG = QuestionActivity::class.java.simpleName
        const val BUNDLE_TASK_ID = "BUNDLE_TASK_ID "
        const val BUNDLE_QUESTION_ID = "BUNDLE_QUESTION_ID "
    }
}