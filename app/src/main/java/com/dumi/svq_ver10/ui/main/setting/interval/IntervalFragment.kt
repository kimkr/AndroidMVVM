package com.dumi.svq_ver10.ui.main.setting.interval

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TimePicker
import com.dumi.svq_ver10.BR
import com.dumi.svq_ver10.R
import com.dumi.svq_ver10.ui.BaseActivity
import com.dumi.svq_ver10.ui.BaseFragment
import com.dumi.svq_ver10.ui.main.MainActivity
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_setting_interval.*
import javax.inject.Inject
import javax.inject.Named

class IntervalFragment : BaseFragment(), View.OnClickListener {

    @field:[Inject Named("IntervalViewModel")]
    lateinit var viewModel: IntervalViewModel
    private var hourOfDay1: Int = 0
    private var hourOfDay2: Int = 0

    private val disposable = CompositeDisposable()

    override fun useDataBinding() = true

    override fun getLayout() = R.layout.fragment_setting_interval

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
    }

    override fun onStart() {
        super.onStart()
        binding.setVariable(BR.viewmodel, viewModel)
        binding.setVariable(BR.onClickListener, this)
//        disposable.add()
        start_time_btn.setOnClickListener(this)
        end_time_btn.setOnClickListener(this)
        set_start_btn.setOnClickListener(this)
        set_end_btn.setOnClickListener(this)
        finish_setting_layout.setOnClickListener(this)
        tp.is24HourView
        val start = viewModel.getTimeSpan().first
        val end = viewModel.getTimeSpan().second
        val interval = viewModel.getInterval()
        start_time_btn.text = viewModel.formatHour(start)
        end_time_btn.text = viewModel.formatHour(end)
        complete_time_txt.setText("$interval")
        complete_time_txt.setOnTouchListener { _, _ ->
            complete_time_txt.isFocusableInTouchMode = true
            complete_time_txt.isFocusable = true
            complete_time_txt.requestFocus()
            complete_time_txt.setText("")
            val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm!!.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
            false
        }
    }

    override fun onDestroy() {
        disposable.clear()
        super.onDestroy()
    }

    override fun onClick(view: View) {
        when (view.id) {
            /* 수집시간설정 메뉴들 클릭 이벤트 */
            R.id.start_time_btn -> {
                tp.visibility = TimePicker.VISIBLE
                set_start_btn.visibility = Button.VISIBLE
            }
            R.id.end_time_btn -> {
                tp.visibility = TimePicker.VISIBLE
                set_end_btn.visibility = Button.VISIBLE
            }
            R.id.set_start_btn -> {
                set_end_btn.visibility = Button.INVISIBLE
                hourOfDay1 = tp.currentHour
                start_time_btn.text = viewModel.formatHour(hourOfDay1)
                tp.visibility = TimePicker.INVISIBLE
                set_start_btn.visibility = Button.INVISIBLE
            }
            R.id.set_end_btn -> {
                set_start_btn.visibility = Button.INVISIBLE
                hourOfDay2 = tp.currentHour
                end_time_btn.text = viewModel.formatHour(hourOfDay2)
                tp.visibility = TimePicker.INVISIBLE
                set_end_btn.visibility = Button.INVISIBLE
            }
            R.id.finish_setting_layout -> {
                viewModel.setTimeSpan(hourOfDay1, hourOfDay2)
                if (complete_time_txt.text.toString().isEmpty()) {
                    (activity as BaseActivity).toast("간격을 설정해주세요!")
                } else {
                    try {
                        viewModel.setInterval(complete_time_txt.text.toString().toInt())
                        (activity as MainActivity).onBackPressed()
                    } catch (e: Exception) {
                        (activity as BaseActivity).toast("숫자로 입력해주세요!")
                    }

                }
            }
        }
    }

    companion object {
        val TAG = IntervalFragment::class.java.simpleName
    }
}
