package com.dumi.svq_ver10.ui.main

enum class Screen(val code: String, val activity: Boolean) {
    HOME("HomeFragment", false),
    NOTIFICATION("NotificationFragment", false),
    SELF_CHECK("SelfCheckFragment", false),
    SETTING("SettingFragment", false),
    WEEKLY_STAT("WeeklyStat", false),
    PROFILE("Profile", false),
    SETTING_ACCOUNT("SettingAccount", true),
    SETTING_HEALING("SettingHealing", true),
    SETTING_LOCATION("SettingLocation", true);

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