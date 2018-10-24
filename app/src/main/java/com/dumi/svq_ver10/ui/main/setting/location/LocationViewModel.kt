package com.dumi.svq_ver10.ui.main.setting.location

import android.arch.lifecycle.ViewModel
import com.dumi.svq_ver10.persistence.repository.SettingRepository

class LocationViewModel(private val settingRepository: SettingRepository) : ViewModel() {

    companion object {
        var TAG = LocationViewModel::class.java.simpleName
    }
}