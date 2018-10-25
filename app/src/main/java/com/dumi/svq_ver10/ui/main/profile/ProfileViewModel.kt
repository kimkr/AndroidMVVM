package com.dumi.svq_ver10.ui.main.profile

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.dumi.svq_ver10.persistence.repository.LocationRepository
import com.dumi.svq_ver10.persistence.repository.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ProfileViewModel(private val userRepository: UserRepository,
                       private val locationRepository: LocationRepository) : ViewModel() {

    val name = ObservableField<String>()
    val email = ObservableField<String>()
    val phone = ObservableField<String>()
    val dob = ObservableField<String>()
    val gender = ObservableField<String>()
    val location = ObservableField<String>()

    fun loadUserProfile(): Disposable = userRepository.getUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { user ->
                name.set(user.name)
                email.set(user.email)
                phone.set(user.phone)
                dob.set(user.dob)
                gender.set(user.gender.code)
                location.set(locationRepository.getAddress())
            }

    companion object {
        var TAG = ProfileViewModel::class.java.simpleName
    }
}