package com.dumi.svq_ver10.ui.question.checkbox

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.dumi.svq_ver10.R
import com.dumi.svq_ver10.di.scopes.FragmentScope
import kotlinx.android.synthetic.main.item_question_checkbox.view.*
import javax.inject.Inject

@FragmentScope
class CheckBoxAdapter @Inject constructor() : BaseAdapter() {

    private var list: List<String> = ArrayList<String>()

    fun replaceData(list: List<String>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        var convertView = view
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.context).inflate(R.layout.item_question_checkbox, parent, false)
        }
        convertView!!.tv_item_question_checkbox.text = getItem(position)
        return convertView
    }

    override fun getItem(position: Int) = list[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = list.size
}