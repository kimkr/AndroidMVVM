package com.dumi.svq_ver10.ui.main.taskincomplete

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.dumi.svq_ver10.R
import com.dumi.svq_ver10.di.scopes.FragmentScope
import com.dumi.svq_ver10.persistence.model.Task
import kotlinx.android.synthetic.main.item_task_incomplete.view.*
import javax.inject.Inject

@FragmentScope
class IncompleteTaskAdapter @Inject constructor() : BaseAdapter() {

    private var list: List<Task> = ArrayList<Task>()

    fun replaceData(list: List<Task>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        var convertView = view
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.context).inflate(R.layout.item_task_incomplete, parent, false)
        }
        convertView!!.tv_item_task_question.text = getItem(position).question
        return convertView
    }

    override fun getItem(position: Int) = list[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = list.size
}