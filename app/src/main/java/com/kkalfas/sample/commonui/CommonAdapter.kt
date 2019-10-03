package com.kkalfas.sample.commonui

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kkalfas.sample.posts.BR
import kotlin.properties.Delegates

interface DiffUtilCompareCallback<Type> {
    fun areItemsTheSame(t1: Type, t2: Type): Boolean
}

interface ItemClickedCallback<Type> {
    fun onItemClick(view: View, item: Type)
}

abstract class CommonAdapter<Type : Any> :
    RecyclerView.Adapter<CommonAdapter<Type>.ViewHolder>(),
    DiffUtilCompareCallback<Type>,
    ItemClickedCallback<Type> {

    var items: List<Type> by Delegates.observable(emptyList()) { _, old, new ->
        selfNotify(old, new, this)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ViewHolder(private val dataBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(dataBinding.root) {
        fun bind(viewModel: Type) {
            dataBinding.root.setOnClickListener {
                onItemClick(it, viewModel)
            }
            dataBinding.setVariable(BR.viewModel, viewModel)
            dataBinding.executePendingBindings()
        }

        fun <T> bindVariable(variableId: Int, variable: T) {
            dataBinding.setVariable(variableId, variable)
        }
    }

    private fun RecyclerView.Adapter<ViewHolder>.selfNotify(
        old: List<Type>,
        new: List<Type>,
        callback: DiffUtilCompareCallback<Type>
    ) {
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return callback.areItemsTheSame(old[oldItemPosition], new[newItemPosition])
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return old[oldItemPosition] == new[newItemPosition]
            }

            override fun getOldListSize() = old.size
            override fun getNewListSize() = new.size
        }).dispatchUpdatesTo(this)
    }
}