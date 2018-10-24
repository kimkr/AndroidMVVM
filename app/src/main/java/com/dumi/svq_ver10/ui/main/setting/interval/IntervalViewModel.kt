package com.dumi.svq_ver10.ui.main.setting.interval

import android.arch.lifecycle.ViewModel
import com.dumi.svq_ver10.persistence.repository.SettingRepository

class IntervalViewModel(private val settingRepository: SettingRepository) : ViewModel() {

    companion object {
        var TAG = IntervalViewModel::class.java.simpleName
    }
}