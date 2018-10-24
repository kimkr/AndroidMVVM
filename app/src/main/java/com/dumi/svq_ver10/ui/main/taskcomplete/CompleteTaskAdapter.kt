package com.dumi.svq_ver10.ui.main.taskcomplete

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.dumi.svq_ver10.R
import com.dumi.svq_ver10.di.scopes.FragmentScope
import com.dumi.svq_ver10.persistence.model.Task
import com.dumi.svq_ver10.util.TimeUtil
import kotlinx.android.synthetic.main.item_task_complete.view.*
import javax.inject.Inject

@FragmentScope
class CompleteTaskAdapter @Inject constructor() : BaseAdapter() {

    private var list: List<Any> = ArrayList<Any>()

    fun replaceData(list: List<Any>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        var convertView = view
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.context).inflate(R.layout.item_task_complete, parent, false)
        }
        var item = getItem(position)
        if (item is Task) {
            convertView!!.rl_item_task_complete_day.visibility = GONE
            convertView!!.rl_item_task_complete_task.visibility = VISIBLE
            convertView!!.tv_item_task_complete_question.text = item.question
            convertView!!.tv_item_task_complete_answer.text = item.answer
            convertView!!.tv_item_task_complete_time.text = TimeUtil.formatToTime(item.answeredAt!!)
        } else {
            convertView!!.rl_item_task_complete_day.visibility = VISIBLE
            convertView!!.rl_item_task_complete_task.visibility = GONE
            convertView!!.tv_item_task_complete_day.text = item as String
        }
        return convertView
    }

    override fun getItem(position: Int) = list[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = list.size
}