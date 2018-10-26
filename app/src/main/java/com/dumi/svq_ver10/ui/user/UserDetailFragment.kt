package com.dumi.svq_ver10.ui.user

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dumi.svq_ver10.R
import dagger.android.support.AndroidSupportInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_user_detail.*
import javax.inject.Inject
import javax.inject.Named

class UserDetailFragment : Fragment() {

    @field:[Inject Named("fragment")]
    lateinit var viewModel: UserViewModel

    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
    }

    override fun onStart() {
        super.onStart()
        disposable.add(
                viewModel.userName()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ this.user_name.text = it },
                        { error -> Log.e(TAG, "Unable to get username", error) }))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_detail, null)
    }

    override fun onDestroy() {
        disposable.clear()
        super.onDestroy()
    }

    companion object {
        private val TAG = UserDetailFragment::class.java.simpleName
    }
}