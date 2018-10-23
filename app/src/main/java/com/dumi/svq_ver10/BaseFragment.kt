package com.dumi.svq_ver10

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseFragment : Fragment() {

    protected lateinit var binding: ViewDataBinding

    abstract fun getLayout(): Int

    abstract fun useDataBinding(): Boolean

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if (!useDataBinding()) {
            inflater.inflate(getLayout(), null)
        } else {
            binding = DataBindingUtil.inflate(inflater, getLayout(), null, false)
            binding.root
        }
    }
}