package com.kkalfas.sample.postsdetails.presentation

import android.view.View
import android.view.ViewGroup
import com.kkalfas.sample.commonui.CommonAdapter
import com.kkalfas.sample.commonui.DataBinding
import com.kkalfas.sample.commonui.LayoutInflaterProvider
import com.kkalfas.sample.posts.BR
import com.kkalfas.sample.posts.R
import com.kkalfas.sample.postsdetails.data.Comment
import javax.inject.Inject

class CommentsAdapter @Inject constructor(
    private val layoutInflaterProvider: LayoutInflaterProvider,
    private val dataBinding: DataBinding
) : CommonAdapter<Comment>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = layoutInflaterProvider.get(parent.context)
        return ViewHolder(dataBinding.inflate(inflater, R.layout.item_comment, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindVariable(BR.position, "#${position + 1}")
        super.onBindViewHolder(holder, position)
    }

    override fun areItemsTheSame(t1: Comment, t2: Comment) = t1 == t2

    override fun onItemClick(view: View, item: Comment) {
        //no op
    }

}
