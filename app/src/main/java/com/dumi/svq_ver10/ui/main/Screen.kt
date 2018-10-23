package com.dumi.svq_ver10.ui.main

enum class Screen(val code: String) {
    HOME("HomeFragment"),
    NOTIFICATION("NotificationFragment"),
    SELF_CHECK("SelfCheckFragment"),
    SETTING("SettingFragment"),
    WEEKLY_STAT("WeeklyStat"),
    PROFILE("Profile"), ;

    companion object {
        fun from(code: String): Screen? {
            for (screen in Screen.values()) {
                if (screen.code == code) {
                    return screen
                }
            }
            return null
        }
    }
}