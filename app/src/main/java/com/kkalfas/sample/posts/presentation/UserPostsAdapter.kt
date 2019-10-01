package com.kkalfas.sample.posts.presentation

import android.view.ViewGroup
import com.kkalfas.sample.commonui.*
import com.kkalfas.sample.posts.R
import com.kkalfas.sample.posts.data.UserPost
import javax.inject.Inject

class UserPostsAdapter @Inject constructor(
    private val layoutInflaterProvider: LayoutInflaterProvider,
    private val dataBinding: DataBinding
) : CommonAdapter<UserPost>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = layoutInflaterProvider.get(parent.context)
        return ViewHolder(dataBinding.inflate(inflater, R.layout.item_user_post, parent, false))
    }

    override fun areItemsTheSame(t1: UserPost, t2: UserPost) = t1 == t2
}