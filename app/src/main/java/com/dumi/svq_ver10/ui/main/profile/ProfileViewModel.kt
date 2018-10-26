package com.dumi.svq_ver10.ui.main.profile

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.dumi.svq_ver10.persistence.repository.*
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ProfileViewModel(private val userRepository: UserRepository,
                       private val locationRepository: LocationRepository,
                       private val taskRepository: TaskRepository,
                       private val questionRepository: QuestionRepository,
                       private val settingRepository: SettingRepository) : ViewModel() {

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

    fun clearProfile(): Completable {
        return Completable.concat(listOf(
                userRepository.deleteAllUsers(),
                Completable.fromCallable { locationRepository.clearAll() },
                taskRepository.clearAll(),
                questionRepository.removeAll(),
                Completable.fromCallable { settingRepository.clearAll() }
        ))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    companion object {
        var TAG = ProfileViewModel::class.java.simpleName
    }
}