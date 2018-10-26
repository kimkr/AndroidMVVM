package com.dumi.svq_ver10.ui.main.setting

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import com.dumi.svq_ver10.persistence.repository.SettingRepository

class SettingViewModel(private val settingRepository: SettingRepository) : ViewModel() {

    val pushAllowed = ObservableBoolean(false)
    val locationAllowed = ObservableBoolean(false)

    fun loadStatus() {
        pushAllowed.set(settingRepository.isPushAllowed())
        locationAllowed.set(settingRepository.isLocationAllowed())
    }

    fun flipPushStatus(status: Boolean) {
        settingRepository.setPushAllowed(status)
        pushAllowed.set(status)
    }

    fun flipLocationStatus(status: Boolean) {
        settingRepository.setLocationAllowed(status)
        locationAllowed.set(status)
    }

    companion object {
        const val TAG = "SettingViewModel"
    }
}