package com.kkalfas.sample.commonui

import android.content.Context
import android.view.LayoutInflater
import javax.inject.Inject

class LayoutInflaterProvider @Inject constructor() {

    fun get(context: Context): LayoutInflater {
        return LayoutInflater.from(context)
    }
}