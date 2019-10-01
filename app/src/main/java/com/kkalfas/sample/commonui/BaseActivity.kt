package com.kkalfas.sample.commonui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import dagger.android.AndroidInjection

abstract class BaseActivity : AppCompatActivity() {
    abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
    }

    inline fun <reified T : ViewDataBinding> AppCompatActivity.bind() : T {
        return DataBindingUtil.setContentView(this, layoutId)
    }
}