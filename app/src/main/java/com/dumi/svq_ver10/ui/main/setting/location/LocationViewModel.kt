package com.dumi.svq_ver10.ui.main.setting.location

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import com.dumi.svq_ver10.persistence.repository.SettingRepository

class LocationViewModel(private val settingRepository: SettingRepository) : ViewModel() {

    val allowed = ObservableBoolean(false)

    fun loadStatus() {
        allowed.set(settingRepository.isGpsAllowed())
    }

    fun flipStatus(status: Boolean) {
        settingRepository.setGpsAllowed(status)
        allowed.set(status)
    }

    companion object {
        var TAG = LocationViewModel::class.java.simpleName
    }
}