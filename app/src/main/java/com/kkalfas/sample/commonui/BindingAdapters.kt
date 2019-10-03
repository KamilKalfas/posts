package com.kkalfas.sample.commonui

import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import com.kkalfas.sample.posts.R
import kotlin.math.max

object BindingAdapters {

    @BindingAdapter("avatar")
    @JvmStatic
    fun avatar(view: ImageView, id: Int) {
        val placeHolder =
            ColorDrawable(ContextCompat.getColor(view.context, android.R.color.darker_gray))
        if (id != -1) {
            Glide.with(view.context)
                .load(PhotoUrlProvider.getAvatarUrl(id))
                .placeholder(placeHolder)
                .circleCrop()
                .into(view)
                .clearOnDetach()
        } else {
            view.setImageDrawable(placeHolder)
        }
    }

    @BindingAdapter("backgroundImage")
    @JvmStatic
    fun backgroundImage(view: ImageView, url: String) {
        val placeHolder =
            ColorDrawable(ContextCompat.getColor(view.context, R.color.colorPrimaryDark))
        if (url.isNotEmpty()) {
            Glide.with(view.context)
                .load(url)
                .placeholder(placeHolder)
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

    @JvmStatic
    @BindingAdapter("postedByFormattedText")
    fun postedByFormatter(view: TextView, text: String) {
        if (text.isNotBlank()) {
            view.text = view.context.resources.getString(R.string.post_details_posted_by, text)
        }
    }

    @JvmStatic
    @BindingAdapter("addOnOffsetChangedListener")
    fun addOnOffsetChangedListener(view: AppBarLayout, imageView: ImageView) {
        val startAnimactionValue = 20
        var avatarVisibility = true
        var maxScrollRange = view.totalScrollRange
        view.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (maxScrollRange == 0) {
                maxScrollRange = appBarLayout.totalScrollRange
            }
            val percentage = Math.abs(verticalOffset) * 100 / maxScrollRange
            if (percentage >= startAnimactionValue && avatarVisibility) {
                avatarVisibility = false
                imageView.animate()
                    .scaleY(0F).scaleX(0F)
                    .setDuration(200)
                    .start()
            }
            if (percentage <= startAnimactionValue && !avatarVisibility) {
                avatarVisibility = true
                imageView.animate()
                    .scaleY(1F).scaleX(1F)
                    .setDuration(200)
                    .start()
            }
        })
    }
}