package com.dumi.svq_ver10.ui.main.setting.healing

import android.arch.lifecycle.ViewModel
import com.dumi.svq_ver10.persistence.repository.SettingRepository

class HealingViewModel(private val settingRepository: SettingRepository) : ViewModel() {

    companion object {
        var TAG = HealingViewModel::class.java.simpleName
    }
}