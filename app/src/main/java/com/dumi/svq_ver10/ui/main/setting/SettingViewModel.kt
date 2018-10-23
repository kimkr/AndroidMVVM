package com.dumi.svq_ver10.ui.main.setting

import android.arch.lifecycle.ViewModel
import com.dumi.svq_ver10.persistence.repository.SettingRepository

class SettingViewModel(private val settingRepository: SettingRepository) : ViewModel() {

    companion object {
        const val TAG = "SettingViewModel"
    }
}