package com.kkalfas.sample.commonui

import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.ImageView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.kkalfas.sample.posts.R

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

    @JvmStatic
    @BindingAdapter("snackbarErrorMessage", "snackbarCallback")
    fun onErrorShowSnackbar(
        coordinatorLayout: CoordinatorLayout,
        errorMessage: Int,
        action: SnackbarActionCallback
    ) {
        if (errorMessage != -1) {
            Snackbar.make(
                coordinatorLayout,
                errorMessage,
                Snackbar.LENGTH_LONG
            ).setAction(R.string.retry, action)
                .show()
        }
    }

    @JvmStatic
    @BindingAdapter("onRefreshListener")
    fun setOnRefreshListener(view: SwipeRefreshLayout, onClickListener: View.OnClickListener) {
        view.setOnRefreshListener { onClickListener.onClick(view) }
    }

    @JvmStatic
    @BindingAdapter("isRefreshing")
    fun isRefreshing(view: SwipeRefreshLayout, isRefreshing: Boolean) {
        view.isRefreshing = isRefreshing
    }
}