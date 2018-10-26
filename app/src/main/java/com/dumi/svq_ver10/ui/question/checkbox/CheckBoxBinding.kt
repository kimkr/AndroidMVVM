package com.dumi.svq_ver10.ui.question.checkbox

import android.databinding.BindingAdapter
import android.widget.ListView

object IncompleteTaskBinding {
    @BindingAdapter("app:options")
    @JvmStatic
    fun setItems(listView: ListView, items: List<String>) {
        with(listView.adapter as CheckBoxAdapter) {
            replaceData(items)
        }
    }

    @BindingAdapter("android:choiceMode")
    @JvmStatic
    fun setItems(listView: ListView, choiceSingle: Boolean) {
        if(choiceSingle){
            listView.choiceMode = ListView.CHOICE_MODE_SINGLE
        } else {
            listView.choiceMode = ListView.CHOICE_MODE_MULTIPLE
        }
    }
}