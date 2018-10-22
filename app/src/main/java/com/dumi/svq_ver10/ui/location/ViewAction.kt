package com.dumi.svq_ver10.ui.location

enum class ViewAction(val loading: Boolean) {
    START_MAP_LOADING(true),
    END_MAP_LOADING(false),
    START_SEARCH(true),
    END_SEARCH(false),
    SHOW_RECENT_SEARCH(false),
    HIDE_RECENT_SEARCH(false),
    CLICK_SEARCH_RESULT(false)
}