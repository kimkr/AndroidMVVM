package com.dumi.svq_ver10.ui.main.profile

import android.app.Activity
import android.os.Bundle
import android.view.View
import com.dumi.svq_ver10.BR
import com.dumi.svq_ver10.R
import com.dumi.svq_ver10.ui.BaseFragment
import com.dumi.svq_ver10.ui.components.SingleDialog
import com.dumi.svq_ver10.ui.main.MainActivity
import com.dumi.svq_ver10.ui.main.Screen
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Named

class ProfileFragment : BaseFragment(), View.OnClickListener {

    @field:[Inject Named("ProfileViewModel")]
    lateinit var viewModel: ProfileViewModel

    private val disposable = CompositeDisposable()

    override fun useDataBinding() = true

    override fun getLayout() = R.layout.fragment_profile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
    }

    override fun onStart() {
        super.onStart()
        binding.setVariable(BR.viewmodel, viewModel)
        binding.setVariable(BR.onClickListener, this)
        disposable.add(viewModel.loadUserProfile())
    }

    override fun onDestroy() {
        disposable.clear()
        super.onDestroy()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_profile_location -> (activity as MainActivity).addScreen(Screen.LOCATION, "Location")
            R.id.btn_profile_logout -> {
                val dialog = SingleDialog(activity as Activity,
                        "로그아웃시 저장된 정보가 삭제됩니다.\n 로그아웃 하시겠습니까?",
                        View.OnClickListener { _ ->
                            viewModel.clearProfile()
                                    .subscribe {
                                        (activity as MainActivity).goTo(Screen.LOGIN)
                                    }

                        })
                dialog.show()
            }
        }
    }

    companion object {
        val TAG = ProfileFragment::class.java.simpleName
    }
}
