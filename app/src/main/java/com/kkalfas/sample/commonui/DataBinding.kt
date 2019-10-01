package com.kkalfas.sample.commonui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import javax.inject.Inject

class DataBinding @Inject constructor() {

    fun inflate(
        inflater: LayoutInflater,
        layoutId: Int,
        parent: ViewGroup,
        attachToParent: Boolean
    ): ViewDataBinding {
        return DataBindingUtil.inflate(inflater, layoutId, parent, attachToParent)
    }
}