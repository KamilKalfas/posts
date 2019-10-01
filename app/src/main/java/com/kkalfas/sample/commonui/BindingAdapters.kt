package com.kkalfas.sample.commonui

import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

object BindingAdapters {

    @BindingAdapter("avatar")
    @JvmStatic
    fun avatar(view: ImageView, id: Int) {
        val placeHolder =
            ColorDrawable(ContextCompat.getColor(view.context, android.R.color.darker_gray))
        if (id != -1) {
            Glide.with(view.context)
                .load("https://i.pravatar.cc/150?u=$id")
                .placeholder(placeHolder)
                .circleCrop()
                .into(view)
                .clearOnDetach()
        } else {
            view.setImageDrawable(placeHolder)
        }
    }

    @JvmStatic
    @BindingAdapter("layoutManager")
    fun layoutManager(recyclerView: RecyclerView, layoutOrientation: Int) {
        if (layoutOrientation == RecyclerView.HORIZONTAL) {
            recyclerView.layoutManager = LinearLayoutManager(
                recyclerView.context,
                RecyclerView.HORIZONTAL,
                false
            )
        } else if (layoutOrientation == RecyclerView.VERTICAL) {
            recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        }
    }
}