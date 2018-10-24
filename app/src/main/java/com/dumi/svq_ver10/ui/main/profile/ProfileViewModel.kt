package com.dumi.svq_ver10.ui.main.profile

import android.arch.lifecycle.ViewModel
import com.dumi.svq_ver10.persistence.repository.SettingRepository

class ProfileViewModel(private val settingRepository: SettingRepository) : ViewModel() {

    companion object {
        var TAG = ProfileViewModel::class.java.simpleName
    }
}