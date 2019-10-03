package com.kkalfas.sample.posts.presentation

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import com.kkalfas.sample.commonui.*
import com.kkalfas.sample.posts.R
import com.kkalfas.sample.posts.data.UserPost
import com.kkalfas.sample.postsdetails.presentation.POST_ID_KEY
import com.kkalfas.sample.postsdetails.presentation.PostDetailsActivity
import com.kkalfas.sample.postsdetails.presentation.USER_ID_KEY
import javax.inject.Inject

class PostsAdapter @Inject constructor(
    private val layoutInflaterProvider: LayoutInflaterProvider,
    private val dataBinding: DataBinding
) : CommonAdapter<UserPost>() {

    override fun onItemClick(view: View, item: UserPost) {
        val context = view.context
        val startDetailsActivityIntent = Intent(context, PostDetailsActivity::class.java)
        startDetailsActivityIntent.putExtra(POST_ID_KEY, item.postId)
        startDetailsActivityIntent.putExtra(USER_ID_KEY, item.userId)
        context.startActivity(startDetailsActivityIntent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = layoutInflaterProvider.get(parent.context)
        return ViewHolder(dataBinding.inflate(inflater, R.layout.item_user_post, parent, false))
    }

    override fun areItemsTheSame(t1: UserPost, t2: UserPost) = t1 == t2
}