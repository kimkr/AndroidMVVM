package com.dumi.svq_ver10.ui.location

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.dumi.svq_ver10.R
import com.dumi.svq_ver10.di.scopes.ActivityScope
import com.dumi.svq_ver10.util.location.GoogleMapService
import kotlinx.android.synthetic.main.item_location_address.view.*
import javax.inject.Inject

@ActivityScope
class SearchResultAdapter @Inject constructor() : BaseAdapter() {

    private var list: List<GoogleMapService.Result> = ArrayList<GoogleMapService.Result>()

    fun replaceData(list: List<GoogleMapService.Result>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        var convertView = view
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.context).inflate(R.layout.item_location_address, parent, false)
        }
        convertView!!.tx_location_address.text = getItem(position).address
        convertView!!.root_item_location_address.isChecked = false
        return convertView
    }

    override fun getItem(position: Int) = list[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = list.size
}