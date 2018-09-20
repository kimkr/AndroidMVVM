package io.github.kimkr.mvvmsample.ui.user

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import io.github.kimkr.mvvmsample.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_user.*
import javax.inject.Inject
import javax.inject.Named


class UserActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @field:[Inject Named("activity")]
    lateinit var viewModel: UserViewModel

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        AndroidInjection.inject(this)
        update_user_button.setOnClickListener { updateUserName() }
        supportFragmentManager
                .beginTransaction()
                .add(R.id.user_detail, UserDetailFragment(), "userDetail")
                .commit()
    }

    override fun onStart() {
        super.onStart()
        disposable.add(viewModel.userName()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ this.user_name.text = it },
                        { error -> Log.e(TAG, "Unable to get username", error) }))
    }

    override fun onStop() {
        super.onStop()
        disposable.clear()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector

    private fun updateUserName() {
        val userName = user_name_input.text.toString()
        update_user_button.isEnabled = false
        disposable.add(viewModel.updateUserName(userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ update_user_button.isEnabled = true },
                        { error -> Log.e(TAG, "Unable to update username", error) })
        )
    }

    companion object {
        private val TAG = UserActivity::class.java.simpleName
    }
}